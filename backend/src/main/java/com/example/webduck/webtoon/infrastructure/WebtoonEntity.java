package com.example.webduck.webtoon.infrastructure;

import com.example.webduck.genre.entity.WebtoonGenre;
import com.example.webduck.global.common.BaseTime;
import com.example.webduck.webtoon.domain.Webtoon;
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
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Table(name = "webtoon")
@Entity
public class WebtoonEntity extends BaseTime {

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

    // 활성화 비활성화 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WebtoonStatus webtoonStatus = WebtoonStatus.INACTIVE;

    // 웹툰 바로가기 주소
    private String webtoonUrl;

    @Column(nullable = false)
    private int reviewCount = 0;

    protected WebtoonEntity() {}

    @Builder
    public WebtoonEntity(Long id, String title, String summary, String originalImageName,
        String imagePath,
        PublishDay publishDay, Platform platform, String author, String webtoonUrl,
        WebtoonStatus webtoonStatus) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.originalImageName = originalImageName;
        this.imagePath = imagePath;
        this.publishDay = publishDay;
        this.platform = platform;
        this.author = author;
        this.webtoonUrl = webtoonUrl;
        this.webtoonStatus = webtoonStatus;
    }

    public static WebtoonEntity from(Webtoon webtoon) {
        return WebtoonEntity.builder()
            .id(webtoon.getId())
            .title(webtoon.getTitle())
            .summary(webtoon.getSummary())
            .originalImageName(webtoon.getOriginalImageName())
            .imagePath(webtoon.getImagePath())
            .publishDay(webtoon.getPublishDay())
            .platform(webtoon.getPlatform())
            .author(webtoon.getAuthor())
            .webtoonUrl(webtoon.getWebtoonUrl())
            .webtoonStatus(webtoon.getWebtoonStatus())
            .build();
    }

    // Entity to Domain
    public Webtoon toModel() {
        return Webtoon.builder()
            .id(id)
            .title(title)
            .summary(summary)
            .originalImageName(originalImageName)
            .imagePath(imagePath)
            .publishDay(publishDay)
            .platform(platform)
            .author(author)
            .webtoonStatus(webtoonStatus)
            .webtoonUrl(webtoonUrl)
            .build();
    }

    // 양방향 관계 객체 연결 (+순환 참조 방지)
    public void addWebtoonGenre(WebtoonGenre webtoonGenre) {
        this.webtoonGenres.add(webtoonGenre);
        if (webtoonGenre.getWebtoon() != this) {
            webtoonGenre.setWebtoon(this);
        }
    }

    public enum WebtoonStatus {
        ACTIVE, // 활성화된 웹툰
        INACTIVE // 비활성화된 웹툰
    }

    @Getter
    @RequiredArgsConstructor
    public enum WebtoonSortCondition {
        RATING("RATING"),
        COUNT("COUNT");
        private final String condition;
    }


}

