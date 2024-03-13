package com.example.webduck.webtoon.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter

public class WebtoonGenreResponse {

    private final Long id;
    private final String title;
    private final String imagePath;
    private final String originalImageName;

    @Builder
    public WebtoonGenreResponse(Long id, String title, String imagePath, String originalImageName) {
        this.id = id;
        this.title = title;
        this.imagePath = imagePath;
        this.originalImageName = originalImageName;
    }
}
