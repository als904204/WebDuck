package com.example.webduck.Webtoon.dto;

import com.example.webduck.Webtoon.entity.PublishDay;
import com.example.webduck.Webtoon.entity.Webtoon;
import lombok.Getter;

@Getter
public class WebtoonRequest {

    private final String title;
    private final String summary;
    private final String originalImageName;
    private final String imagePath;
    private final PublishDay publishDay;
    public WebtoonRequest(Webtoon webtoon) {
        this.title = webtoon.getTitle();
        this.summary = webtoon.getSummary();
        this.originalImageName = webtoon.getOriginalImageName();
        this.imagePath = webtoon.getImagePath();
        this.publishDay = webtoon.getPublishDay();
    }
}
