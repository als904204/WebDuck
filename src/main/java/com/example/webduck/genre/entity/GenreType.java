package com.example.webduck.genre.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum GenreType {

    // 로맨스
    ROMANCE("로맨스"),
    // 판타지
    FANTASY("판타지"),
    // 개그
    GAG("개그"),
    // 무협
    MARTIAL_ARTS("무협"),
    // 성인
    ADULT("성인");

    private final String type;
}
