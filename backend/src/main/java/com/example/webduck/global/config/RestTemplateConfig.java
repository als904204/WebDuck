package com.example.webduck.global.config;


import java.util.concurrent.TimeUnit;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() {
        HttpClient httpClient = HttpClientBuilder.create()
            .setMaxConnTotal(50)  // 전체 최대 연결 수 설정
            .setMaxConnPerRoute(20)  // 호스트(IP+Port) 당 최대 연결 수 설정
            .evictIdleConnections(60L, TimeUnit.SECONDS)  // 유휴 상태인 연결을 일정 시간 후 제거
            .evictExpiredConnections()  // 만료된 연결 제거
            .setConnectionTimeToLive(30, TimeUnit.SECONDS)  // 연결의 최대 수명 설정
            .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())  // Keep alive 사용
            .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectionRequestTimeout(5000);  // 연결 요청 최대 대기 시간 (5초)
        factory.setConnectTimeout(5000);  // 연결 설정 최대 시간
        factory.setReadTimeout(5000);  // 응답 읽기 최대 시간
        factory.setHttpClient(httpClient);

        return new RestTemplate(factory);
    }
}