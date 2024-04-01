package com.example.webduck.webtoon.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.webduck.webtoon.domain.Webtoon;
import com.example.webduck.webtoon.infrastructure.Platform;
import com.example.webduck.webtoon.infrastructure.PublishDay;
import com.example.webduck.webtoon.infrastructure.WebtoonEntity.WebtoonStatus;
import com.example.webduck.webtoon.service.api.WebtoonKorResponse;
import com.example.webduck.webtoon.service.api.WebtoonKyuResponse;
import com.example.webduck.webtoon.service.api.WebtoonKyuResponse.WebtoonKyuItem;
import com.example.webduck.webtoon.service.port.WebtoonRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@ActiveProfiles("dev")
@Slf4j
@SpringBootTest
class WebtoonOpenApiTest {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    WebtoonRepository webtoonRepository;


    @Test
    void saveKorWebtoons() {
        /**
         *  title           : kor.title
         *  originalImageName : kor.title+jpg
         *  imagePath        : kor.img
         *  publishDay       : kor.updateDays
         *  platform        : kor.service.toString()
         *  webtoonUrl       : kor.url
         *  author          : kyu.pictrWritrNm + " " + kyu.sntncWritrNm
         *  summary         : kyu.outline
         */

        /**
         * korea webtoon api
         */
        String korEndpoint = "https://korea-webtoon-api.herokuapp.com/";

        String korNaverUri = UriComponentsBuilder.fromHttpUrl(korEndpoint)
            .queryParam("perPage", 0)
            .queryParam("service", "naver")
            .build()
            .toUriString();


        WebtoonKorResponse korWebtoonResponse = restTemplate.getForEntity(korNaverUri, WebtoonKorResponse.class)
            .getBody();

        if (korWebtoonResponse != null) {


            List<Webtoon> webtoons = korWebtoonResponse.getWebtoons().stream()
                .map(item -> Webtoon.builder()
                    .title(item.getTitle())
                    .originalImageName(item.getTitle() + ".jpg")
                    .imagePath(item.getImg())
                    .publishDay(PublishDay.fromString(item.getPublishDay()))
                    .platform(Platform.fromString(item.getService()))
                    .webtoonUrl(item.getUrl())
                    .author(null)
                    .summary(null)
                    .webtoonStatus(WebtoonStatus.INACTIVE)
                    .build()).collect(Collectors.toList());


            webtoonRepository.saveAll(webtoons);
        }
    }

    WebtoonKyuResponse requestKyuApi(String platformName, int pageNo, int viewItemCnt) {
        String kyuEndpoint = "https://www.kmas.or.kr/openapi/search/bookAndWebtoonList";
        String kyuSecretKey = "fb9d29708ea1903b86bbdfd012cf1189";

        String kyuUriString = UriComponentsBuilder.fromHttpUrl(kyuEndpoint)
            .queryParam("prvKey", kyuSecretKey)
            .queryParam("pageNo", pageNo)
            .queryParam("viewItemCnt", viewItemCnt)
            .queryParam("pltfomCdNm", platformName)
            .build()
            .toUriString();

        return restTemplate.getForEntity(kyuUriString, WebtoonKyuResponse.class).getBody();
    }


    @Test
    void compareKyuWithKor() throws InterruptedException {

        // 요청할 개수
        final int viewItemCount = 100;

        // 제목이 고유하므로 KEY ,
        Map<String, WebtoonKyuItem> itemMap = new HashMap<>();

        // 초기 요청으로 api totalCount 알아내기
        int totalCount = requestKyuApi("네이버웹툰", 0, viewItemCount).getTotalCount();

        // 총 페이지 수 구하기
        // 2403의 totalCount가 있다면, 100개씩 요청 -> 총 25페이지 요청 해야 됨
        int totalPages = (totalCount + viewItemCount) / viewItemCount;

        IntStream.range(0, totalPages).forEach(pageNo->{
            try {
                WebtoonKyuResponse res = requestKyuApi("네이버웹툰", pageNo, viewItemCount);
                if (res.isSuccess() && !res.isEmpty()) {
                    res.getItemList().forEach(item ->
                        itemMap.putIfAbsent(item.getPrdctNm(), item)
                    );
                }
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        int size = itemMap.size();
        System.out.println("총 api 수" + size);

        // 2. 요청한 api 객체의 title과 DB에 INACTIVE 상태인 Webtoon들의 title과 비교한다. (일단 findAll() 사용중)
        List<Webtoon> inactiveWebtoons = webtoonRepository.findAll();


        // 3. 같을 경우, 해당 Webtoon을 컬럼을 수정하고 ACTIVE 상태로 수정한뒤 업데이트 쿼리를 날린다.
        List<Webtoon> matchedWebtoonList = new ArrayList<>();

        inactiveWebtoons.forEach(webtoon -> {
            WebtoonKyuItem matchedWebtoon = itemMap.get(webtoon.getTitle());
            if (matchedWebtoon != null) {
                webtoon = webtoon.updateToActive(matchedWebtoon);
                matchedWebtoonList.add(webtoon);
            }
        });
        int updatedSize = matchedWebtoonList.size();

        System.out.println("updatedSize = " + updatedSize);
        webtoonRepository.saveAll(matchedWebtoonList);

    }
}