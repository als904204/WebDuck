package com.example.webduck.webtoon.entity;

import com.example.webduck.genre.entity.WebtoonGenre;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;

@Entity
public class Webtoon{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 제목
    @Column(nullable = false,length = 40)
    private String title;

    // 줄거리
    @Column(nullable = false)
    private String summary;

    // 썸네일 이미지 파일 원본이름
    @Column(nullable = false)
    private String originalImageName;

    // 썸네일 이미지 경로
    @Column(nullable = false)
    private String imagePath;

    // 웹툰 요일
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PublishDay publishDay;

    // 웹툰 플랫폼
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Platform platform;

    // 웹툰 장르
    @OneToMany(mappedBy = "webtoon",fetch = FetchType.LAZY)
    private List<WebtoonGenre> webtoonGenres = new ArrayList<>();

    // 웹툰 작가
    @Column(nullable = false,length = 30)
    private String author;

    private String webtoonUrl;

    protected Webtoon() {}

    @Builder
    public Webtoon(String title, String summary, String originalImageName, String imagePath,
        PublishDay publishDay, Platform platform,String author) {
        this.title = title;
        this.summary = summary;
        this.originalImageName = originalImageName;
        this.imagePath = imagePath;
        this.publishDay = publishDay;
        this.platform = platform;
        this.author = author;
    }

    // 양방향 관계 객체 연결 (+순환 참조 방지)
    public void addWebtoonGenre(WebtoonGenre webtoonGenre) {
        this.webtoonGenres.add(webtoonGenre);
        if (webtoonGenre.getWebtoon() != this) {
            webtoonGenre.setWebtoon(this);
        }
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
    public List<WebtoonGenre> getWebtoonGenres() {
        return webtoonGenres;
    }

    public String getAuthor() {
        return author;
    }

    public String getWebtoonUrl() {
        return webtoonUrl;
    }
}

