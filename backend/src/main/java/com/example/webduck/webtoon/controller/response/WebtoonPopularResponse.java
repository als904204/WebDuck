package com.example.webduck.webtoon.controller.response;

import lombok.Getter;

@Getter
public class WebtoonPopularResponse {

    private final Long id;
    private final String title;
    private final String imagePath;
    private final String originalImageName;
    private final String summary;
    private final String webtoonUrl;
    private final int reviewCount;
    private final Double ratingAvg;

    public WebtoonPopularResponse(Long id, String title, String imagePath, String originalImageName,
        String summary, String webtoonUrl, int reviewCount, Double ratingAvg) {
        this.id = id;
        this.title = title;
        this.imagePath = imagePath;
        this.originalImageName = originalImageName;
        this.summary = summary;
        this.webtoonUrl = webtoonUrl;
        this.reviewCount = reviewCount;
        this.ratingAvg = ratingAvg;
    }

}
