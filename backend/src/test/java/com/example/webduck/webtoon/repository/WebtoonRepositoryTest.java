package com.example.webduck.webtoon.repository;


import static org.assertj.core.api.Assertions.assertThat;

import com.example.webduck.config.ConverterConfigTest;
import com.example.webduck.config.QueryDslConfigTest;
import com.example.webduck.genre.entity.Genre;
import com.example.webduck.genre.entity.WebtoonGenre;
import com.example.webduck.webtoon.entity.Platform;
import com.example.webduck.webtoon.entity.PublishDay;
import com.example.webduck.webtoon.entity.Webtoon;
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
class WebtoonRepositoryTest {

    @Autowired
    private WebtoonRepository webtoonRepository;

    @Autowired
    private TestEntityManager testEntityManager;


    private final String title = "화산귀환";
    private final String summary = "줄거리";
    private final String imagePath = "화산귀환";
    private final String originalImageName = "adfafd123.png";

    private final String author = "작가";

    Webtoon savedWebtoon;
    Webtoon martialArtsWebtoon;
    Webtoon fantasyWebtoon;

    List<Webtoon> webtoons = new ArrayList<>();

    @BeforeEach
    void setUp() {

        // 웹툰 한 개
        this.savedWebtoon = webtoonRepository.save(
            Webtoon.builder()
                .title(title)
                .summary(summary)
                .imagePath(imagePath)
                .publishDay(PublishDay.THURSDAY)
                .platform(Platform.NAVER)
                .originalImageName(originalImageName)
                .author(author)
                .build());

        // 웹툰 데이터 목록 생성
        this.webtoons = List.of(
            Webtoon.builder().title("Webtoon 1").summary("Summary 1").imagePath("Path 1")
                .author(author)
                .publishDay(PublishDay.MONDAY).originalImageName("Image1.png")
                .platform(Platform.NAVER).build(),

            Webtoon.builder().title("Webtoon 2").summary("Summary 2").imagePath("Path 2")
                .author(author)
                .publishDay(PublishDay.FRIDAY).originalImageName("Image2.png")
                .platform(Platform.NAVER).build(),

            Webtoon.builder().title("Webtoon 3").summary("Summary 3").imagePath("Path 3")
                .author(author)
                .publishDay(PublishDay.SUNDAY).originalImageName("Image3.png")
                .platform(Platform.NAVER).build(),

            Webtoon.builder().title("Webtoon 4").summary("Summary 4").imagePath("Path 4")
                .author(author)
                .publishDay(PublishDay.SUNDAY).originalImageName("Image4.png")
                .platform(Platform.NAVER).build()
        );

        webtoonRepository.saveAll(webtoons);
    }

    @AfterEach
    void tearDown() {
        this.savedWebtoon = null;
        this.webtoons = null;
        webtoonRepository.deleteAll();
    }


    @DisplayName("웹툰 저장")
    @Test
    void saveWebtoon() {
        assertThat(savedWebtoon.getTitle()).isEqualTo(title);
        assertThat(savedWebtoon.getSummary()).isEqualTo(summary);
        assertThat(savedWebtoon.getImagePath()).isEqualTo(imagePath);
        assertThat(savedWebtoon.getPublishDay()).isEqualTo(PublishDay.THURSDAY);
        assertThat(savedWebtoon.getPlatform()).isEqualTo(Platform.NAVER);
        assertThat(savedWebtoon.getOriginalImageName()).isEqualTo(originalImageName);
    }

    @DisplayName("요일별 웹툰 조회")
    @Test
    void findWebtoonByPublishDay() {

        // when
        List<Webtoon> mondayWebtoons = webtoonRepository.findWebtoonsByPublishDay(
            PublishDay.MONDAY);     // 1개
        List<Webtoon> fridayWebtoons = webtoonRepository.findWebtoonsByPublishDay(
            PublishDay.FRIDAY);     // 1개
        List<Webtoon> thursdayWebtoons = webtoonRepository.findWebtoonsByPublishDay(
            PublishDay.THURSDAY); // 1개
        List<Webtoon> sundayWebtoons = webtoonRepository.findWebtoonsByPublishDay(
            PublishDay.SUNDAY);     // 2개

        // then
        Assertions.assertThat(mondayWebtoons).isNotNull();
        Assertions.assertThat(fridayWebtoons).isNotNull();
        Assertions.assertThat(thursdayWebtoons).isNotNull();
        Assertions.assertThat(sundayWebtoons).isNotNull();

        // 개수
        Assertions.assertThat(mondayWebtoons).hasSize(1);
        Assertions.assertThat(fridayWebtoons).hasSize(1);
        Assertions.assertThat(thursdayWebtoons).hasSize(1);
        Assertions.assertThat(sundayWebtoons).hasSize(2);

        // 요일 확인
        Assertions.assertThat(mondayWebtoons)
            .allMatch(mondayWebtoon -> mondayWebtoon.getPublishDay() == PublishDay.MONDAY);

        Assertions.assertThat(fridayWebtoons)
            .allMatch(fridayWebtoon -> fridayWebtoon.getPublishDay() == PublishDay.FRIDAY);

        Assertions.assertThat(fridayWebtoons)
            .allMatch(thursdayWebtoon -> thursdayWebtoon.getPublishDay() == PublishDay.FRIDAY);

        Assertions.assertThat(fridayWebtoons)
            .allMatch(sundayWebtoon -> sundayWebtoon.getPublishDay() == PublishDay.FRIDAY);

    }

    @DisplayName("플랫폼 별 웹툰 조회")
    @Test
    void findWebtoonByPlatform() {
        // 위에 NAVER 5개 저장됨
        List<Webtoon> naverWebtoons = webtoonRepository.findWebtoonsByPlatform(Platform.NAVER);
        Assertions.assertThat(naverWebtoons).isNotNull();
        Assertions.assertThat(naverWebtoons).hasSize(5);

        Assertions.assertThat(naverWebtoons)
            .allMatch(naver -> naver.getPlatform() == Platform.NAVER);

    }

    @DisplayName("단건 장르별 웹툰 조회")
    @Test
    void findWebtoonByGenreType() {
        final String martialArts = "무협";
        final String fantasy = "판타지";

        // 장르 저장
        Genre martialArtsGenre = new Genre(martialArts);
        Genre fantasyGenre = new Genre(fantasy);
        testEntityManager.persist(martialArtsGenre);
        testEntityManager.persist(fantasyGenre);

        // 웹툰 저장
        martialArtsWebtoon = webtoonRepository.save(
            Webtoon.builder()
                .title(martialArts)
                .summary(summary)
                .author(author)
                .imagePath(imagePath)
                .publishDay(PublishDay.THURSDAY)
                .platform(Platform.NAVER)
                .originalImageName(originalImageName)
                .build());

        fantasyWebtoon = webtoonRepository.save(
            Webtoon.builder()
                .author(author)
                .title(fantasy)
                .summary(summary)
                .imagePath(imagePath)
                .publishDay(PublishDay.THURSDAY)
                .platform(Platform.NAVER)
                .originalImageName(originalImageName)
                .build());

        // 웹툰장르 생성 후 연관관계 설정
        WebtoonGenre martialArtWebtoonGenre = new WebtoonGenre();
        martialArtWebtoonGenre.setGenre(martialArtsGenre);
        martialArtWebtoonGenre.setWebtoon(martialArtsWebtoon);

        WebtoonGenre fantasyWebtoonGenre = new WebtoonGenre();
        fantasyWebtoonGenre.setGenre(fantasyGenre);
        fantasyWebtoonGenre.setWebtoon(fantasyWebtoon);

        testEntityManager.persist(martialArtWebtoonGenre);
        testEntityManager.persist(fantasyWebtoonGenre);

        List<Webtoon> foundMartialWebtoons = webtoonRepository.findWebtoonsByGenreName(martialArts);
        List<Webtoon> foundFantasyWebtoons = webtoonRepository.findWebtoonsByGenreName(fantasy);

        assertThat(foundMartialWebtoons).hasSize(1);
        assertThat(foundFantasyWebtoons).hasSize(1);

        assertThat(foundFantasyWebtoons.get(0).getTitle()).isEqualTo(fantasy);
        assertThat(foundMartialWebtoons.get(0).getTitle()).isEqualTo(martialArts);


    }


}