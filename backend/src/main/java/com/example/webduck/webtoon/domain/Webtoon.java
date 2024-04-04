package com.example.webduck.webtoon.domain;

import com.example.webduck.genre.entity.WebtoonGenre;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.webtoon.infrastructure.Platform;
import com.example.webduck.webtoon.infrastructure.PublishDay;
import com.example.webduck.webtoon.service.port.KyuWebtoonResponse.WebtoonKyu;
import com.example.webduck.webtoon.service.port.KoreaWebtoonResponse.WebtoonKor;
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

    public static Webtoon fromWebtoonKor(WebtoonKor webtoonKor) {
        return Webtoon.builder()
            .title(webtoonKor.getTitle())
            .originalImageName(webtoonKor.getTitle() + ".jpg")
            .imagePath(webtoonKor.getImg())
            .platform(Platform.fromString(webtoonKor.getService()))
            .publishDay(PublishDay.fromString(webtoonKor.getPublishDay()))
            .webtoonUrl(webtoonKor.getUrl())
            .author("")
            .summary("")
            .build();
    }

    public Webtoon merge(WebtoonKyu webtoonKyu) {
        return Webtoon.builder()
            .id(id)
            .title(title)
            .originalImageName(originalImageName)
            .imagePath(imagePath)
            .publishDay(publishDay)
            .platform(platform)
            .webtoonGenres(webtoonGenres)
            .webtoonUrl(webtoonUrl)
            .reviewCount(reviewCount)
            .summary(webtoonKyu.getOutline())
            .author("그림 작가 " + webtoonKyu.getPictrWritrNm() + ", 글 작가 " + webtoonKyu.getSntncWritrNm())
            .build();
    }
}
