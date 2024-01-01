package com.example.webduck.webtoon.dto;

import com.example.webduck.webtoon.entity.Platform;
import com.example.webduck.webtoon.entity.PublishDay;
import com.example.webduck.webtoon.entity.Webtoon;
import lombok.Getter;

@Getter
public class WebtoonRequest {

    private final String title;
    private final String summary;
    private final String originalImageName;
    private final String imagePath;
    private final PublishDay publishDay;
    private final Platform platform;
    public WebtoonRequest(Webtoon webtoon) {
        this.title = webtoon.getTitle();
        this.summary = webtoon.getSummary();
        this.originalImageName = webtoon.getOriginalImageName();
        this.imagePath = webtoon.getImagePath();
        this.publishDay = webtoon.getPublishDay();
        this.platform = webtoon.getPlatform();
    }
}
