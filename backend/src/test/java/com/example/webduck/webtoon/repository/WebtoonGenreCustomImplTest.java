package com.example.webduck.webtoon.repository;



import static org.assertj.core.api.Assertions.assertThat;

import com.example.webduck.config.ConverterConfigTest;
import com.example.webduck.config.QueryDslConfigTest;

import com.example.webduck.genre.entity.Genre;
import com.example.webduck.genre.entity.QGenre;
import com.example.webduck.genre.entity.QWebtoonGenre;
import com.example.webduck.genre.entity.WebtoonGenre;
import com.example.webduck.webtoon.dto.WebtoonGenreResponse;
import com.example.webduck.webtoon.entity.Platform;
import com.example.webduck.webtoon.entity.PublishDay;
import com.example.webduck.webtoon.entity.QWebtoon;
import com.example.webduck.webtoon.entity.Webtoon;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
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
class WebtoonGenreCustomImplTest {

    @Autowired
    private TestEntityManager testEntityManager;
    private EntityManager em;

    // 웹툰 정보
    private final String summary = "줄거리";
    private final String imagePath = "화산귀환";
    private final String originalImageName = "adfafd123.png";
    private String gfWebtoonTitle = "개그 판타지 웹툰";
    private String gWebtoonTitle = "개그 웹툰";

    // 개그만 포함하고있는 웹툰, 개그 판타지를 포함하고 있는 웹툰 객체
    private Webtoon onlyGagWebtoon;
    private Webtoon gagFantasyWebtoon;

    // 장르 (개그,판타지)
    final String gag = "GAG";
    final String fantasy = "FANTASY";
    final String author = "AUTHOR";

    @BeforeEach
    void init() {
        em = testEntityManager.getEntityManager();

        // 장르 저장
        Genre gagGenre = new Genre(gag);
        Genre fantasyGenre = new Genre(fantasy);
        testEntityManager.persist(gagGenre);
        testEntityManager.persist(fantasyGenre);

        // 웹툰 저장
        gagFantasyWebtoon = testEntityManager.persist(
            Webtoon.builder()
                .title(gfWebtoonTitle)
                .summary(summary)
                .imagePath(imagePath)
                .publishDay(PublishDay.THURSDAY)
                .platform(Platform.NAVER)
                .author(author)
                .originalImageName(originalImageName)
                .build());

        onlyGagWebtoon = testEntityManager.persist(
            Webtoon.builder()
                .title(gWebtoonTitle)
                .summary(summary)
                .imagePath(imagePath)
                .author(author)
                .publishDay(PublishDay.THURSDAY)
                .platform(Platform.NAVER)
                .originalImageName(originalImageName)
                .build());

        // 첫 번째 웹툰 : (개그) 장르 설정
        WebtoonGenre gagWebtoonGenre = new WebtoonGenre();
        gagWebtoonGenre.setGenre(gagGenre);
        gagWebtoonGenre.setWebtoon(gagFantasyWebtoon);
        testEntityManager.persist(gagWebtoonGenre);
        // 첫 번째 웹툰 : (판타지) 장르 설정
        WebtoonGenre fantasyWebtoonGenre = new WebtoonGenre();
        fantasyWebtoonGenre.setGenre(fantasyGenre);
        fantasyWebtoonGenre.setWebtoon(gagFantasyWebtoon);
        testEntityManager.persist(fantasyWebtoonGenre);

        // 두번째 웹툰 : (개그) 장르 설정
        WebtoonGenre onlyGag = new WebtoonGenre();
        onlyGag.setGenre(gagGenre);
        onlyGag.setWebtoon(onlyGagWebtoon);
        testEntityManager.persist(onlyGag);


    }

    @DisplayName("개그 장르를 포함한 웹툰은 2개")
    @Test
    void findGagWebtoonsByGenreNames() {
        List<String> genreNamesRequest = Arrays.asList(gag);

        JPAQuery<Webtoon> query = new JPAQuery<>(em);

        QWebtoon webtoon = new QWebtoon("w");
        QWebtoonGenre webtoonGenre = new QWebtoonGenre("wg");
        QGenre genre = new QGenre("g");

        List<WebtoonGenreResponse> result = query.select(
                Projections.constructor(WebtoonGenreResponse.class,
                    webtoon.id,
                    webtoon.title,
                    webtoon.imagePath,
                    webtoon.originalImageName
                ))
            .from(webtoon)
            .innerJoin(webtoonGenre).on(webtoonGenre.webtoon.id.eq(webtoon.id))
            .innerJoin(genre).on(genre.id.eq(webtoonGenre.genre.id))
            .where(genre.name.in(genreNamesRequest))
            .groupBy(webtoon.id)
            .having(genre.name.countDistinct().eq((long) genreNamesRequest.size()))
            .fetch();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTitle()).isEqualTo(gfWebtoonTitle);
        assertThat(result.get(1).getTitle()).isEqualTo(gWebtoonTitle);
    }

    @DisplayName("개그 판타지 장르를 포함한 웹툰은 1개")
    @Test
    void findGagFantasyWebtoonsByGenreNames() {
        List<String> genreNamesRequest = Arrays.asList(gag, fantasy);

        JPAQuery<Webtoon> query = new JPAQuery<>(em);

        QWebtoon webtoon = new QWebtoon("w");
        QWebtoonGenre webtoonGenre = new QWebtoonGenre("wg");
        QGenre genre = new QGenre("g");

        List<WebtoonGenreResponse> result = query.select(
                Projections.constructor(WebtoonGenreResponse.class,
                    webtoon.id,
                    webtoon.title,
                    webtoon.imagePath,
                    webtoon.originalImageName
                ))
            .from(webtoon)
            .innerJoin(webtoonGenre).on(webtoonGenre.webtoon.id.eq(webtoon.id))
            .innerJoin(genre).on(genre.id.eq(webtoonGenre.genre.id))
            .where(genre.name.in(genreNamesRequest))
            .groupBy(webtoon.id)
            .having(genre.name.countDistinct().eq((long) genreNamesRequest.size()))
            .fetch();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo(gfWebtoonTitle);

    }


}