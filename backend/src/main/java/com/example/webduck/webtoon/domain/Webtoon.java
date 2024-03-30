package com.example.webduck.webtoon.domain;

import com.example.webduck.genre.entity.WebtoonGenre;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.webtoon.infrastructure.Platform;
import com.example.webduck.webtoon.infrastructure.PublishDay;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Webtoon {

    private Long id;
    private String title;
    private String summary;
    private String originalImageName;
    private String imagePath;
    private PublishDay publishDay;
    private Platform platform;
    private List<WebtoonGenre> webtoonGenres;
    private String author;
    private String webtoonUrl;
    private int reviewCount;

    @Builder
    public Webtoon(Long id, String title, String summary, String originalImageName,
        String imagePath, PublishDay publishDay, Platform platform,
        List<WebtoonGenre> webtoonGenres, String author, String webtoonUrl, int reviewCount) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.originalImageName = originalImageName;
        this.imagePath = imagePath;
        this.publishDay = publishDay;
        this.platform = platform;
        this.webtoonGenres = webtoonGenres;
        this.author = author;
        this.webtoonUrl = webtoonUrl;
        this.reviewCount = reviewCount;
    }



    public void incrementReviewCount() {
        ++this.reviewCount;
    }

    public static void validateWebtoonIds(List<Webtoon> actualWebtoons, List<Long> webtoonIds) {
        int actual = actualWebtoons.size();
        int expect = webtoonIds.size();
        if (actual != expect) {
            throw new CustomException(LogicExceptionCode.BAD_REQUEST);
        }
    }
}
