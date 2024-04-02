package com.example.webduck.webtoon.service;

import static java.lang.Thread.sleep;

import com.example.webduck.genre.repository.GenreRepository;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.webtoon.domain.Webtoon;
import com.example.webduck.webtoon.infrastructure.WebtoonEntity.WebtoonStatus;
import com.example.webduck.webtoon.service.port.KoreaWebtoonApiResponse;
import com.example.webduck.webtoon.infrastructure.Platform;
import com.example.webduck.webtoon.service.port.KyuWebtoonApiResponse;
import com.example.webduck.webtoon.service.port.KyuWebtoonApiResponse.WebtoonKyuItem;
import com.example.webduck.webtoon.service.port.WebtoonRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@Service
public class WebtoonApiClient {


    @Value("${webtoon.kor.url}")
    private String korUrl;

    @Value("${webtoon.kyu.url}")
    private String kyuUrl;

    private final RestTemplate restTemplate;

    private final WebtoonRepository webtoonRepository;
    private final GenreRepository genreRepository;


    /**
     * <p>Korea Webtoon API 에 플랫폼에 해당하는 웹툰 요청 후 저장한다.</p>
     * @param platform NAVER,KAKAO
     * @return 저장된 개수
     */
    @Transactional
    public int saveKorApiByPlatform(Platform platform) {

        validateExistsInitData(platform);

        String uri = buildKorRequestUri(korUrl,platform.name());

        KoreaWebtoonApiResponse response = fetchWebtoonsFromApi(uri,
            KoreaWebtoonApiResponse.class);
        log.info("Successfully fetched Korea Webtoon API");


        List<Webtoon> webtoons = response.getWebtoons().stream()
            .map(Webtoon::fromWebtoonKorItem)
            .collect(Collectors.toList());

        log.info("Korea Webtoon API size = {}", webtoons.size());

        int savedSize = webtoonRepository.saveAll(webtoons).size();

        log.info("Successfully saved Korea Webtoon API [Size is={}]", savedSize);
        return savedSize;
    }


    /**
     * <p>만화 규장각 api 요청하여, DB 에 있는 INACTIVE 와 비교하여 같을 경우 ACTIVE로 수정한다.</p>
     * @param platform NAVER,KAKAO
     * @return         수정된 개수
     */
    @Transactional
    public int activateMatchedWebtoonsWithKyuApi(Platform platform) {

        String reqPlatformName = platform.toKorean();

        int totalCount = getTotalCountFromKyuApi(reqPlatformName);
        int totalPages = calculateTotalPages(totalCount);

        Map<String, WebtoonKyuItem> webtoonMap = requestKyuApi(reqPlatformName, totalPages);

        List<Webtoon> inactiveWebtoons = webtoonRepository.findAllByStatus(WebtoonStatus.INACTIVE);
        List<Webtoon> matchedWebtoons = matchedWebtoonsToActive(inactiveWebtoons, webtoonMap);


        return webtoonRepository.saveAll(matchedWebtoons).size();
    }

    private List<Webtoon> matchedWebtoonsToActive(List<Webtoon> inactiveWebtoons, Map<String, WebtoonKyuItem> webtoonMap) {
        List<Webtoon> matchedWebtoonList = new ArrayList<>();

        inactiveWebtoons.forEach(webtoon -> {
            WebtoonKyuItem matchedWebtoon = webtoonMap.get(webtoon.getTitle());
            if (matchedWebtoon != null) {
                Webtoon updatedWebtoon = webtoon.updateToActive(matchedWebtoon);
                matchedWebtoonList.add(updatedWebtoon);
            }
        });

        log.info("matchedWebtoon count = {}",matchedWebtoonList.size());
        return matchedWebtoonList;
    }





    /**
     * <p>이미 요청 platofrm에 해당하는 데이터가 있다면 중복 저장 방지 예외</p>
     * @param platform
     */
    private void validateExistsInitData(Platform platform) {
        if (webtoonRepository.existsByPlatform(platform)) {
            log.warn("already exists {} data",platform.name());
            throw new CustomException(LogicExceptionCode.DUPLICATE_REQUEST);
        }
    }

    /**
     * <p>Korea Webtoon API 요청 URI 생성</p>
     * @param url           Korea Webtoon url
     * @param platformName  naver,kakao
     * @return              완성된 URI
     */
    private String buildKorRequestUri(String url, String platformName) {
        return UriComponentsBuilder.fromHttpUrl(url)
            .queryParam("perPage", 0)
            .queryParam("service", platformName)
            .build()
            .toUriString();
    }

    /**
     * <p>요청 전 총 api 개수 계산</p>
     *
     * @param platformName 네이버웹툰,카카오웹툰,카카오페이지
     * @return 해당 api 서버 총 아이템 수
     */
    private int getTotalCountFromKyuApi(String platformName) {
        String uri = buildKyuRequestUri(platformName, 0);
        KyuWebtoonApiResponse response = fetchWebtoonsFromApi(uri, KyuWebtoonApiResponse.class);
        return response.getTotalCount();
    }

    /**
     * <p>총 아이템 개수와 요청 아이템 개수로 몇 페이지를 요청해야할지 계산한다.</p>
     *
     * @param totalCount 해당 api 총 아이템 수
     * @return 요청해야할 페이지 개수
     */
    private int calculateTotalPages(int totalCount) {
        return (totalCount + 100 - 1) / 100;
    }

    /**
     * <p>만화 규장각 open api에 100개씩 요청한다.</p>
     *
     * @param platformName 네이버웹툰,카카오웹툰
     * @param totalPages   총 페이지 수
     * @return : KEY : 웹툰 제목, VALUE : 만화 규장각 객체
     */
    private Map<String, WebtoonKyuItem> requestKyuApi(String platformName, int totalPages) {
        Map<String, WebtoonKyuItem> webtoonMap = new HashMap<>();
        IntStream.range(0, totalPages)
            .forEach(pageNo -> {
                String uri = buildKyuRequestUri(platformName, pageNo);
                try {
                    KyuWebtoonApiResponse response = fetchWebtoonsFromApi(uri,
                        KyuWebtoonApiResponse.class);

                    if (response.isSuccess() && !response.isEmpty()) {
                        log.info("여기");
                        response.getItemList()
                            .forEach(item-> webtoonMap.putIfAbsent(item.getPrdctNm(), item));
                    }
                    sleep(40);
                } catch (InterruptedException e) {
                    log.error("Sleep interrupted", e);
                    Thread.currentThread().interrupt();
                    throw new CustomException(LogicExceptionCode.THREAD_INTERRUPTED_ERROR);
                }
            });
        return webtoonMap;
    }

    /**
     * <p>만화 규장각에 요청할 request uri를 만든다.</p>
     *
     * @param platformName 네이버웹툰,카카오웹툰
     * @param pageNo       페이지
     * @return 완성된 uri
     */
    private String buildKyuRequestUri(String platformName, int pageNo) {
        String kyuKey = "fb9d29708ea1903b86bbdfd012cf1189";
        return UriComponentsBuilder.fromHttpUrl(kyuUrl)
            .queryParam("prvKey", kyuKey)
            .queryParam("pageNo", pageNo)
            .queryParam("viewItemCnt", 100)
            .queryParam("pltfomCdNm", platformName)
            .build()
            .toUriString();
    }

    /**
     * @param requestUri   요청할 api uri
     * @param responseType 응답 api mapping class
     * @return 응답 api mapping class
     */
    private <T> T fetchWebtoonsFromApi(String requestUri, Class<T> responseType) {
        log.info("target API URI={}",requestUri);
        log.info("fetching Webtoon API...");
        return restTemplate.getForEntity(requestUri, responseType).getBody();
    }


}
