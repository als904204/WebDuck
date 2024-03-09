package com.example.webduck.webtoon.entity;

import com.example.webduck.genre.entity.WebtoonGenre;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.OneToMany;
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
    @Column(nullable = false,length = 600)
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

    // 웹툰 바로가기 주소
    private String webtoonUrl;

    @Column(nullable = false)
    private int reviewCount = 0;

    protected Webtoon() {}

    @Builder
    public Webtoon(String title, String summary, String originalImageName, String imagePath,
        PublishDay publishDay, Platform platform, String author,String webtoonUrl) {
        this.title = title;
        this.summary = summary;
        this.originalImageName = originalImageName;
        this.imagePath = imagePath;
        this.publishDay = publishDay;
        this.platform = platform;
        this.author = author;
        this.webtoonUrl = webtoonUrl;
    }

    public void incrementReviewCount() {
        ++this.reviewCount;
    }

    // 양방향 관계 객체 연결 (+순환 참조 방지)
    public void addWebtoonGenre(WebtoonGenre webtoonGenre) {
        this.webtoonGenres.add(webtoonGenre);
        if (webtoonGenre.getWebtoon() != this) {
            webtoonGenre.setWebtoon(this);
        }
    }

    public static void validateSizeMismatch(int actual,int expected) {
        if (actual != expected) {
            throw new CustomException(LogicExceptionCode.BAD_REQUEST);
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

    public int getReviewCount() {
        return reviewCount;
    }
}

