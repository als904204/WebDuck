package com.example.webduck.genre.repository;


import static org.assertj.core.api.Assertions.assertThat;

import com.example.webduck.config.ConverterConfigTest;
import com.example.webduck.config.QueryDslConfigTest;
import com.example.webduck.genre.entity.Genre;
import com.example.webduck.genre.entity.WebtoonGenre;
import com.example.webduck.webtoon.infrastructure.Platform;
import com.example.webduck.webtoon.infrastructure.PublishDay;
import com.example.webduck.webtoon.infrastructure.WebtoonEntity;
import com.example.webduck.webtoon.infrastructure.WebtoonJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Import({QueryDslConfigTest.class, ConverterConfigTest.class})
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
class WebtoonEntityGenreRepositoryTest {

    @Autowired
    private WebtoonJpaRepository webtoonJpaRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private WebtoonGenreRepository webtoonGenreRepository;


    @DisplayName("웹툰장르<==>웹툰,장르 연관관계 테스트")
    @Test
    void shouldPersistWebtoonGenreWithWebtoonAndGenre() {

        // given

        // 웹툰,장르 생성 및 저장
        WebtoonEntity webtoonEntity = WebtoonEntity.builder()
            .title("Webtoon 1")
            .summary("Summary 1").imagePath("Path 1")
            .publishDay(PublishDay.MONDAY)
            .originalImageName("Image1.png")
            .author("author")
            .platform(Platform.NAVER).build();

        webtoonJpaRepository.save(webtoonEntity);

        Genre genre = new Genre("romance");
        genreRepository.save(genre);

        // 웹툰_장르 <==> 웹툰
        // 웹툰_장르 <==> 장르
        // 연관관계 설정 후 DB 저장
        WebtoonGenre webtoonGenre = new WebtoonGenre();
        webtoonGenre.setWebtoon(webtoonEntity);      // 양방향
        webtoonEntity.addWebtoonGenre(webtoonGenre); // 양방향

        webtoonGenre.setGenre(genre);          // 단방향

        // when
        webtoonGenreRepository.save(webtoonGenre);

        // then
        WebtoonGenre foundWebtoonGenre = webtoonGenreRepository.findById(1L).orElseThrow();
        assertThat(foundWebtoonGenre).isNotNull();
        assertThat(foundWebtoonGenre.getWebtoon().getId()).isEqualTo(webtoonEntity.getId());
        assertThat(foundWebtoonGenre.getGenre().getName()).isEqualTo(genre.getName());

        WebtoonGenre wgFromWebtoon = webtoonEntity.getWebtoonGenres().get(0);
        assertThat(wgFromWebtoon).isEqualTo(webtoonGenre);
    }
}