package com.example.webduck.webtoon.infrastructure;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Platform {
    NAVER("네이버웹툰"),
    KAKAO("카카오웹툰"),
    ELSE("기타웹툰");


    private final String koreanName;

    public static Platform fromString(String platform) {
        switch (platform.toLowerCase()) {
            case "naver":
                return NAVER;
            case "kakao":
            case "kakao_page":
                return KAKAO;
            default:
                return ELSE;
        }
    }

    // 만화 규장각 API 는 플랫폼을 한국어로 요청 보내야 한다.
    public String toKorean() {
        return koreanName;
    }
}
