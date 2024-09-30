package com.example.webduck.webtoon.domain;

import com.example.webduck.genre.entity.WebtoonGenre;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.webtoon.infrastructure.Platform;
import com.example.webduck.webtoon.infrastructure.PublishDay;
import com.example.webduck.webtoon.service.port.KyuWebtoonResponse.WebtoonKyu;
import com.example.webduck.webtoon.service.port.KoreaWebtoonResponse.WebtoonKor;
import java.util.List;
import java.util.UUID;
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

        // 없다면 썸네일 UUID로 설정
        String thumbnail = webtoonKor.getThumbnail().stream()
            .findFirst()
            .orElse(UUID.randomUUID().toString());

        // 없다면 연재 종료된 웹툰
        String updateDays = webtoonKor.getUpdateDays().stream()
            .findFirst()
            .orElse("finished");

        return Webtoon.builder()
            .title(webtoonKor.getTitle())
            .originalImageName(webtoonKor.getTitle() + ".jpg")
            .imagePath(thumbnail)
            .platform(Platform.fromString(webtoonKor.getProvider()))
            .publishDay(PublishDay.fromString(updateDays))
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
