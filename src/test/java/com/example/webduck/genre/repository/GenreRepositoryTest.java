package com.example.webduck.genre.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.example.webduck.genre.entity.Genre;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
class GenreRepositoryTest {

    @Autowired
    private GenreRepository genreRepository;

    private final String type = "무협";

    Genre genre;

    @BeforeEach
    void setUp() {
        genre = new Genre(type);
        genre = genreRepository.save(genre);

    }

    @AfterEach
    void cleanUp() {
        genre = null;
        genreRepository.deleteAll();
    }

    @DisplayName("장르 저장")
    @Test
    void testGenreSave() {
        assertThat(genre.getType()).isNotNull();
        assertThat(genre.getType()).isEqualTo(type);
    }

    @DisplayName("타입으로 장르 찾기")
    @Test
    void testFindGenreByType() {
        Genre foundGenre = genreRepository.findByType(type)
            .orElseThrow(() -> new IllegalArgumentException("can't find the genre type"));

        assertThat(foundGenre).isNotNull();
        assertThat(foundGenre.getType()).isEqualTo(type);
    }

}