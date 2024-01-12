package com.example.webduck.webtoon.dto;

import com.example.webduck.review.dto.ReviewResponse;
import com.example.webduck.webtoon.entity.Platform;
import com.example.webduck.webtoon.entity.PublishDay;
import com.example.webduck.webtoon.entity.Webtoon;
import java.util.List;

public class WebtoonDetails {

    private final Long webtoonId;
    private final String title;
    private final String summary;
    private final String originalImageName;
    private final String imagePath;
    private final PublishDay publishDay;
    private final Platform platform;
    private final String author;
    private final List<ReviewResponse> reviews;


    public WebtoonDetails(Webtoon webtoon,List<ReviewResponse> reviews) {
        this.webtoonId = webtoon.getId();
        this.originalImageName = webtoon.getOriginalImageName();
        this.imagePath = webtoon.getImagePath();
        this.title = webtoon.getTitle();
        this.summary = webtoon.getSummary();
        this.publishDay = webtoon.getPublishDay();
        this.platform = webtoon.getPlatform();
        this.author = webtoon.getAuthor();
        this.reviews = reviews;
    }

    public Long getWebtoonId() {
        return webtoonId;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getOriginalImageName() {
        return originalImageName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public PublishDay getPublishDay() {
        return publishDay;
    }

    public Platform getPlatform() {
        return platform;
    }

    public String getAuthor() {
        return author;
    }

    public List<ReviewResponse> getReviews() {
        return reviews;
    }
}
