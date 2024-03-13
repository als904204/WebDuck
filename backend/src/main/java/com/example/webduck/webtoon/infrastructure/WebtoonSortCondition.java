package com.example.webduck.webtoon.infrastructure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WebtoonSortCondition {
    RATING("RATING"),
    COUNT("COUNT");
    private final String condition;
}
