package com.example.webduck.webtoon.service.port;

import com.example.webduck.webtoon.infrastructure.Platform;
import com.example.webduck.webtoon.service.port.KoreaWebtoonResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class KoreaApiClient {

    @Value("${webtoon.kor.url}")
    private String url;
    private final RestTemplate restTemplate;


    public KoreaWebtoonResponse getWebtoons(Platform platform) {
        String targetUri = buildKorRequestUri(platform);
        log.info("fetch korea webtoon target uri={}", targetUri);
        return restTemplate.getForObject(targetUri, KoreaWebtoonResponse.class);
    }

    private String buildKorRequestUri(Platform platform) {
        String service = platform.name().toLowerCase();
        return UriComponentsBuilder.fromHttpUrl(url)
            .queryParam("perPage", 0)
            .queryParam("service", service)
            .build()
            .toUriString();
    }

}
