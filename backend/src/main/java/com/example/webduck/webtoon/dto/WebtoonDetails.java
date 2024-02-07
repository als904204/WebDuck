package com.example.webduck.webtoon.dto;

import com.example.webduck.review.dto.ReviewResponse;
import com.example.webduck.webtoon.entity.Platform;
import com.example.webduck.webtoon.entity.PublishDay;
import com.example.webduck.webtoon.entity.Webtoon;
import java.util.List;
import lombok.Getter;

@Getter
public class WebtoonDetails {

    private final Long webtoonId;
    private final String title;
    private final String summary;
    private final String originalImageName;
    private final String imagePath;
    private final PublishDay publishDay;
    private final Platform platform;
    private final String author;
    private final String webtoonUrl;
    private final List<ReviewResponse> reviews;
    private final int reviewCount;
    private final Double webtoonRating;


    public WebtoonDetails(Webtoon webtoon, List<ReviewResponse> reviews, int reviewCount,
        Double webtoonRating) {
        this.webtoonId = webtoon.getId();
        this.originalImageName = webtoon.getOriginalImageName();
        this.imagePath = webtoon.getImagePath();
        this.title = webtoon.getTitle();
        this.summary = webtoon.getSummary();
        this.publishDay = webtoon.getPublishDay();
        this.platform = webtoon.getPlatform();
        this.author = webtoon.getAuthor();
        this.reviews = reviews;
        this.webtoonUrl = webtoon.getWebtoonUrl();
        this.reviewCount = reviewCount;
        this.webtoonRating = webtoonRating;
    }
}
