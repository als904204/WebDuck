package com.example.webduck.webtoon.domain;

import com.example.webduck.genre.entity.WebtoonGenre;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.webtoon.service.port.KoreaWebtoonApiResponse.WebtoonKorItem;
import com.example.webduck.webtoon.infrastructure.Platform;
import com.example.webduck.webtoon.infrastructure.PublishDay;
import com.example.webduck.webtoon.infrastructure.WebtoonEntity.WebtoonStatus;
import com.example.webduck.webtoon.service.port.KyuWebtoonApiResponse.WebtoonKyuItem;
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
    private WebtoonStatus webtoonStatus;



    @Builder
    public Webtoon(Long id, String title, String summary, String originalImageName,
        String imagePath, PublishDay publishDay, Platform platform,
        List<WebtoonGenre> webtoonGenres, String author, String webtoonUrl, int reviewCount,WebtoonStatus webtoonStatus) {
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
        this.webtoonStatus = webtoonStatus;
    }

    public static Webtoon fromWebtoonKorItem(WebtoonKorItem item) {
        return Webtoon.builder()
            .title(item.getTitle())
            .originalImageName(item.getTitle() + ".jpg")
            .imagePath(item.getImg())
            .publishDay(PublishDay.fromString(item.getPublishDay()))
            .platform(Platform.fromString(item.getService()))
            .webtoonUrl(item.getUrl())
            .author("")
            .summary("")
            .webtoonStatus(WebtoonStatus.INACTIVE)
            .build();
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

    public Webtoon updateToActive(WebtoonKyuItem webtoonKyuItem) {
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
            .webtoonStatus(WebtoonStatus.ACTIVE)
            .summary(webtoonKyuItem.getOutline())
            .author(webtoonKyuItem.getPictrWritrNm() + "," + webtoonKyuItem.getSntncWritrNm())
            .build();
    }

}
