package com.example.webduck.webtoon.infrastructure;

import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.webtoon.service.port.KyuWebtoonResponse;
import com.example.webduck.webtoon.service.port.KyuWebtoonResponse.WebtoonKyu;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Slf4j
@RequiredArgsConstructor
@Service
public class KyuAsyncApiClient {


    @Value("${webtoon.kyu.url}")
    private String url;

    @Value("${webtoon.kyu.key}")
    private String key;

    private final RestTemplate restTemplate;

    private final ExternalApiRequestLogRepository externalApiRequestLogRepository;


    // 비동기로 만화 규장각 API 요청
    public CompletableFuture<Map<String, WebtoonKyu>> getWebtoonsAsync(Platform platform) {

        // 마지막 요청한 페이지 조회
        ExternalApiRequestLog apiRequestLog = findRequestLogByPlatform(platform);

        String platformToKorean = platform.toKorean();
        log.info("start fetching Kyu API by platform={}", platformToKorean);

        int startPage = apiRequestLog.getStartPage();
        int lastPage = apiRequestLog.getLastPage();

        // 만화 규장각 API 트래픽 & 마지막 페이지 검사
        validateApiServerRequest(platformToKorean, startPage);

        ExecutorService executor = Executors.newFixedThreadPool(10);

        // 멀티 쓰레드 환경 동시성 고려
        AtomicInteger lastRequestPage = new AtomicInteger();

        // K : 제목, V : 웹툰 정보 API 결과
        Map<String, WebtoonKyu> webtoonKyuMap = new ConcurrentHashMap<>();

        // API 결과 리턴 값을 webtoonKyuMap에 넣기 때문에 supplyAsync 사용 X
        CompletableFuture<Void> fetchAllPages = CompletableFuture.runAsync(() -> {
            IntStream.rangeClosed(startPage, lastPage)
                .parallel()
                .forEach(pageNo -> {
                    String uri = buildKyuRequestUri(platformToKorean, pageNo);
                    KyuWebtoonResponse response = restTemplate.getForEntity(uri,
                        KyuWebtoonResponse.class).getBody();
                    assert response != null;
                    try {
                        // 제목으로 중복처리
                        response.getItemList()
                            .forEach(item -> webtoonKyuMap.putIfAbsent(item.getPrdctNm(), item));
                        lastRequestPage.updateAndGet(x -> Math.max(x, pageNo));
                    } catch (Exception e) {
                        log.error("Kyu API Request Error={}", e.getMessage());
                        throw new CustomException(LogicExceptionCode.SERVER_ERROR);
                    }

                });
        }, executor);

        // 비동기처리 완료될때까지 Blocking
        return fetchAllPages.thenApply(v -> {
            apiRequestLog.setLastRequestedPage(lastRequestPage.get());
            externalApiRequestLogRepository.save(apiRequestLog);
            executor.shutdown();
            return webtoonKyuMap;
        });
    }


    public ExternalApiRequestLog findRequestLogByPlatform(Platform platform) {
        return externalApiRequestLogRepository.findByPlatform(platform)
            .orElseGet(() -> new ExternalApiRequestLog(platform));
    }


    private String buildKyuRequestUri(String platformName, int pageNo) {
        final int itemCount = 20;
        return UriComponentsBuilder.fromHttpUrl(url)
            .queryParam("prvKey", key)
            .queryParam("pageNo", pageNo)
            .queryParam("viewItemCnt", itemCount)
            .queryParam("pltfomCdNm", platformName)
            .build()
            .toUriString();
    }

    private void validateApiServerRequest(String platformKeyword, int startPage) {
        String uri = buildKyuRequestUri(platformKeyword, startPage);
        KyuWebtoonResponse response = restTemplate.getForEntity(uri,
            KyuWebtoonResponse.class).getBody();

        // 더 이상 요청할 페이지가 없을 경우
        if (startPage > response.getTotalCount() || response.getItemList().isEmpty()) {
            log.error("kyu : Failed to fetch data, requested the last page.");
            throw new CustomException(LogicExceptionCode.RANGE_NOT_SATISFIABLE);
        }

        // 일일 트래픽 요청량이 초과 되었을 경우
        if (!response.isSuccess()) {
            log.error(
                "kyu : Failed to fetch data, error message: {}, error status: {}",
                response.getResultMessage(), response.getResultState());
            throw new CustomException(LogicExceptionCode.MAXIMUM_TRAFFIC);
        }
    }
}
