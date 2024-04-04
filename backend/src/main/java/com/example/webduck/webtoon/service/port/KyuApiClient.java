package com.example.webduck.webtoon.service.port;

import com.example.webduck.webtoon.infrastructure.ExternalApiRequestLog;
import com.example.webduck.webtoon.infrastructure.Platform;
import com.example.webduck.webtoon.infrastructure.ExternalApiRequestLogRepository;
import com.example.webduck.webtoon.service.port.KyuWebtoonResponse.WebtoonKyu;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@Service
public class KyuApiClient {

    @Value("${webtoon.kyu.url}")
    private String url;

    private final RestTemplate restTemplate;

    private final ExternalApiRequestLogRepository externalApiRequestLogRepository;

    public Map<String, WebtoonKyu> getWebtoons(Platform platform) {

        ExternalApiRequestLog apiRequestLog = externalApiRequestLogRepository.findByPlatform(platform)
            .orElseGet(() -> new ExternalApiRequestLog(platform));

        log.info("start fetching Kyu API");
        String platformKeyword = platform.toKorean();
        Map<String, WebtoonKyu> webtoonKyuMap = new ConcurrentHashMap<>();


        int startPage = apiRequestLog.getLastRequestedPage() + 1; // 마지막으로 요청된 페이지 이후부터 시작
        int maxPage = startPage + 29;

        // 마지막 api 요청 페이지부터, 최대 페이지까지 요청한다.
        // 트래픽 제한 걸려 실패할 경우, 반복문을 중단한다.
        for (int pageNo = startPage; pageNo <= maxPage; pageNo++) {
            String uri = buildKyuRequestUri(platformKeyword, pageNo);
            KyuWebtoonResponse response = restTemplate.getForEntity(uri, KyuWebtoonResponse.class).getBody();

            if (response != null && response.isSuccess() && !response.isEmpty()) {
                response.getItemList().forEach(item -> webtoonKyuMap.putIfAbsent(item.getPrdctNm(), item));
                log.info("Successfully fetched page: {}, in: {}", pageNo, maxPage);
                apiRequestLog.setLastRequestedPage(pageNo);
            } else {
                log.error("kyu : Failed to fetch data, error message: {}, error status: {}",
                    response.getResultMessage(), response.getResultMessage());
                break; // 요청 중단
            }
        }

        int updatedLastRequestedPage = externalApiRequestLogRepository.save(apiRequestLog)
            .getLastRequestedPage();

        log.info(
            "kyu : Finished fetching webtoons. Total unique webtoons fetched: {}, Last requested page: {}",
            webtoonKyuMap.size(), updatedLastRequestedPage);

        return webtoonKyuMap;
    }

    private String buildKyuRequestUri(String platformName, int pageNo) {
        String kyuKey = "fb9d29708ea1903b86bbdfd012cf1189";
        int viewItemCnt = 50;
        return UriComponentsBuilder.fromHttpUrl(url)
            .queryParam("prvKey", kyuKey)
            .queryParam("pageNo", pageNo)
            .queryParam("viewItemCnt", viewItemCnt)
            .queryParam("pltfomCdNm", platformName)
            .build()
            .toUriString();
    }

}
