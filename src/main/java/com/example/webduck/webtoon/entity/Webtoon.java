package com.example.webduck.webtoon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;

@Entity
public class Webtoon{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 제목
    private String title;

    // 줄거리
    private String summary;

    // 파일의 원본 이름을 저장하면 나중에 관리자가 해당 파일을 식별하고 검색하는 용이
    private String originalImageName;

    // 썸네일 이미지 경로
    private String imagePath;

    // 웹툰 요일
    @Enumerated(EnumType.STRING)
    private PublishDay publishDay;

    // 웹툰 플랫폼
    @Enumerated(EnumType.STRING)
    private Platform platform;

    protected Webtoon() {}

    @Builder
    public Webtoon(String title, String summary, String originalImageName, String imagePath,
        PublishDay publishDay, Platform platform) {
        this.title = title;
        this.summary = summary;
        this.originalImageName = originalImageName;
        this.imagePath = imagePath;
        this.publishDay = publishDay;
        this.platform = platform;
    }

    public Long getId() {
        return id;
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
}
