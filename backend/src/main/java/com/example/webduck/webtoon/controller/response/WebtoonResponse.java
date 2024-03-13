package com.example.webduck.webtoon.controller.response;

import com.example.webduck.webtoon.domain.Webtoon;
import com.example.webduck.webtoon.infrastructure.Platform;
import com.example.webduck.webtoon.infrastructure.PublishDay;
import com.example.webduck.webtoon.infrastructure.WebtoonEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WebtoonResponse {

    private final Long id;
    private final String title;
    private final String summary;
    private final String originalImageName;
    private final String imagePath;
    private final PublishDay publishDay;
    private final Platform platform;
    private final String author;

    public static WebtoonResponse from(Webtoon webtoon) {
        return WebtoonResponse.builder()
            .id(webtoon.getId())
            .title(webtoon.getTitle())
            .summary(webtoon.getSummary())
            .originalImageName(webtoon.getOriginalImageName())
            .imagePath(webtoon.getImagePath())
            .publishDay(webtoon.getPublishDay())
            .platform(webtoon.getPlatform())
            .author(webtoon.getAuthor())
            .build();
    }
}
