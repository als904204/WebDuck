package com.example.webduck.webtoon.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.webduck.mock.review.FakeReviewRepository;
import com.example.webduck.mock.webtoon.FakeWebtoonRepository;
import com.example.webduck.review.domain.Review;
import com.example.webduck.webtoon.controller.response.WebtoonDetails;
import com.example.webduck.webtoon.controller.response.WebtoonResponse;
import com.example.webduck.webtoon.domain.Webtoon;
import com.example.webduck.webtoon.infrastructure.Platform;
import com.example.webduck.webtoon.infrastructure.PublishDay;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WebtoonServiceImplTest {

    private WebtoonServiceImpl webtoonService;

    @BeforeEach
    void init() {
        FakeWebtoonRepository fakeWebtoonRepository = new FakeWebtoonRepository();
        FakeReviewRepository fakeReviewRepository = new FakeReviewRepository();

        webtoonService = WebtoonServiceImpl.builder()
            .webtoonRepository(fakeWebtoonRepository)
            .reviewRepository(fakeReviewRepository)
            .build();

        fakeWebtoonRepository.save(Webtoon.builder()
            .id(1L)
            .title("title")
            .summary("summary")
            .originalImageName("name")
            .imagePath("path")
            .platform(Platform.NAVER)
            .publishDay(PublishDay.MONDAY)
            .webtoonUrl("url")
            .author("author")
            .reviewCount(5)
            .author("WebDuck")
            .build());

        fakeReviewRepository.save(Review.builder()
            .id(1L)
            .memberId(1L)
            .reviewerNickname("WebDuck")
            .webtoonId(1L)
            .content("fun")
            .rating(5)
            .likesCount(0)
            .build());
    }
    @Test
    void getWebtoonDetails() {
        WebtoonDetails webtoonDetails = webtoonService.getWebtoonDetails(1L);

        assertThat(webtoonDetails.getWebtoonId()).isEqualTo(1L);
        assertThat(webtoonDetails.getWebtoonUrl()).isEqualTo("url");
        assertThat(webtoonDetails.getTitle()).isEqualTo("title");
        assertThat(webtoonDetails.getSummary()).isEqualTo("summary");
        assertThat(webtoonDetails.getOriginalImageName()).isEqualTo("name");
        assertThat(webtoonDetails.getPlatform()).isEqualTo(Platform.NAVER);
        assertThat(webtoonDetails.getPublishDay()).isEqualTo(PublishDay.MONDAY);
        assertThat(webtoonDetails.getAuthor()).isEqualTo("WebDuck");
        assertThat(webtoonDetails.getReviewCount()).isEqualTo(1);
    }

    @Test
    void findAll() {
        List<WebtoonResponse> webtoons = webtoonService.findAll();


        assertThat(webtoons.size()).isEqualTo(1);
        assertThat(webtoons.get(0).getId()).isEqualTo(1L);
        assertThat(webtoons.get(0).getTitle()).isEqualTo("title");
        assertThat(webtoons.get(0).getSummary()).isEqualTo("summary");
        assertThat(webtoons.get(0).getOriginalImageName()).isEqualTo("name");
        assertThat(webtoons.get(0).getImagePath()).isEqualTo("path");
        assertThat(webtoons.get(0).getPlatform()).isEqualTo(Platform.NAVER);
        assertThat(webtoons.get(0).getPublishDay()).isEqualTo(PublishDay.MONDAY);
        assertThat(webtoons.get(0).getAuthor()).isEqualTo("WebDuck");
    }

    @Test
    void findWebtoonsByPublishDay() {
        List<WebtoonResponse> webtoons = webtoonService.findWebtoonsByPublishDay(
            PublishDay.MONDAY);
        assertThat(webtoons.size()).isEqualTo(1);
    }

    @Test
    void findWebtoonsByPublishDay_zero() {
        List<WebtoonResponse> webtoons = webtoonService.findWebtoonsByPublishDay(
            PublishDay.SUNDAY);
        assertThat(webtoons.size()).isZero();
    }

    @Test
    void findWebtoonsByPlatform() {
        List<WebtoonResponse> webtoons = webtoonService.findWebtoonsByPlatform(
            Platform.NAVER);

        assertThat(webtoons.size()).isEqualTo(1);
    }

    @Test
    void findWebtoonsByPlatform_zero() {
        List<WebtoonResponse> webtoons = webtoonService.findWebtoonsByPlatform(
            Platform.KAKAO);

        assertThat(webtoons.size()).isZero();
    }

}