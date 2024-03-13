package com.example.webduck.webtoon.infrastructure;


import static org.assertj.core.api.Assertions.assertThat;

import com.example.webduck.config.ConverterConfigTest;
import com.example.webduck.config.QueryDslConfigTest;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@Import({QueryDslConfigTest.class, ConverterConfigTest.class})
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
class WebtoonEntityRepositoryTest {

    @Autowired
    private WebtoonJpaRepository webtoonJpaRepository;

    @Autowired
    private TestEntityManager testEntityManager;


    private final String title = "화산귀환";
    private final String summary = "줄거리";
    private final String imagePath = "화산귀환";
    private final String originalImageName = "adfafd123.png";

    private final String author = "작가";

    WebtoonEntity savedWebtoonEntity;
    WebtoonEntity martialArtsWebtoonEntity;
    WebtoonEntity fantasyWebtoonEntity;

    List<WebtoonEntity> webtoonEntities = new ArrayList<>();

    @BeforeEach
    void setUp() {

        // 웹툰 한 개
        this.savedWebtoonEntity = webtoonJpaRepository.save(
            WebtoonEntity.builder()
                .title(title)
                .summary(summary)
                .imagePath(imagePath)
                .publishDay(PublishDay.THURSDAY)
                .platform(Platform.NAVER)
                .originalImageName(originalImageName)
                .author(author)
                .build());

        // 웹툰 데이터 목록 생성
        this.webtoonEntities = List.of(
            WebtoonEntity.builder().title("Webtoon 1").summary("Summary 1").imagePath("Path 1")
                .author(author)
                .publishDay(PublishDay.MONDAY).originalImageName("Image1.png")
                .platform(Platform.NAVER).build(),

            WebtoonEntity.builder().title("Webtoon 2").summary("Summary 2").imagePath("Path 2")
                .author(author)
                .publishDay(PublishDay.FRIDAY).originalImageName("Image2.png")
                .platform(Platform.NAVER).build(),

            WebtoonEntity.builder().title("Webtoon 3").summary("Summary 3").imagePath("Path 3")
                .author(author)
                .publishDay(PublishDay.SUNDAY).originalImageName("Image3.png")
                .platform(Platform.NAVER).build(),

            WebtoonEntity.builder().title("Webtoon 4").summary("Summary 4").imagePath("Path 4")
                .author(author)
                .publishDay(PublishDay.SUNDAY).originalImageName("Image4.png")
                .platform(Platform.NAVER).build()
        );

        webtoonJpaRepository.saveAll(webtoonEntities);
    }

    @AfterEach
    void tearDown() {
        this.savedWebtoonEntity = null;
        this.webtoonEntities = null;
        webtoonJpaRepository.deleteAll();
    }


    @DisplayName("웹툰 저장")
    @Test
    void saveWebtoon() {
        assertThat(savedWebtoonEntity.getTitle()).isEqualTo(title);
        assertThat(savedWebtoonEntity.getSummary()).isEqualTo(summary);
        assertThat(savedWebtoonEntity.getImagePath()).isEqualTo(imagePath);
        assertThat(savedWebtoonEntity.getPublishDay()).isEqualTo(PublishDay.THURSDAY);
        assertThat(savedWebtoonEntity.getPlatform()).isEqualTo(Platform.NAVER);
        assertThat(savedWebtoonEntity.getOriginalImageName()).isEqualTo(originalImageName);
    }

    @DisplayName("요일별 웹툰 조회")
    @Test
    void findWebtoonByPublishDay() {

        // when
        List<WebtoonEntity> mondayWebtoonEntities = webtoonJpaRepository.findWebtoonsByPublishDay(
            PublishDay.MONDAY);     // 1개
        List<WebtoonEntity> fridayWebtoonEntities = webtoonJpaRepository.findWebtoonsByPublishDay(
            PublishDay.FRIDAY);     // 1개
        List<WebtoonEntity> thursdayWebtoonEntities = webtoonJpaRepository.findWebtoonsByPublishDay(
            PublishDay.THURSDAY); // 1개
        List<WebtoonEntity> sundayWebtoonEntities = webtoonJpaRepository.findWebtoonsByPublishDay(
            PublishDay.SUNDAY);     // 2개

        // then
        Assertions.assertThat(mondayWebtoonEntities).isNotNull();
        Assertions.assertThat(fridayWebtoonEntities).isNotNull();
        Assertions.assertThat(thursdayWebtoonEntities).isNotNull();
        Assertions.assertThat(sundayWebtoonEntities).isNotNull();

        // 개수
        Assertions.assertThat(mondayWebtoonEntities).hasSize(1);
        Assertions.assertThat(fridayWebtoonEntities).hasSize(1);
        Assertions.assertThat(thursdayWebtoonEntities).hasSize(1);
        Assertions.assertThat(sundayWebtoonEntities).hasSize(2);

        // 요일 확인
        Assertions.assertThat(mondayWebtoonEntities)
            .allMatch(mondayWebtoon -> mondayWebtoon.getPublishDay() == PublishDay.MONDAY);

        Assertions.assertThat(fridayWebtoonEntities)
            .allMatch(fridayWebtoon -> fridayWebtoon.getPublishDay() == PublishDay.FRIDAY);

        Assertions.assertThat(fridayWebtoonEntities)
            .allMatch(thursdayWebtoon -> thursdayWebtoon.getPublishDay() == PublishDay.FRIDAY);

        Assertions.assertThat(fridayWebtoonEntities)
            .allMatch(sundayWebtoon -> sundayWebtoon.getPublishDay() == PublishDay.FRIDAY);

    }

    @DisplayName("플랫폼 별 웹툰 조회")
    @Test
    void findWebtoonByPlatform() {
        // 위에 NAVER 5개 저장됨
        List<WebtoonEntity> naverWebtoonEntities = webtoonJpaRepository.findWebtoonsByPlatform(Platform.NAVER);
        Assertions.assertThat(naverWebtoonEntities).isNotNull();
        Assertions.assertThat(naverWebtoonEntities).hasSize(5);

        Assertions.assertThat(naverWebtoonEntities)
            .allMatch(naver -> naver.getPlatform() == Platform.NAVER);

    }
}