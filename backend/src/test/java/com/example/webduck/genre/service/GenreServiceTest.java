package com.example.webduck.genre.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.webduck.genre.dto.GenreResponse;
import com.example.webduck.genre.entity.Genre;
import com.example.webduck.genre.repository.GenreRepository;
import com.example.webduck.global.exception.CustomException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class GenreServiceTest {

    @InjectMocks
    GenreService genreService;

    @Mock
    GenreRepository genreRepository;

    @DisplayName("모든 장르 찾기")
    @Test
    void findAllGenres() {

        // given
        List<Genre> genres = Arrays.asList(
            new Genre("무협"),
            new Genre("성인"),
            new Genre("판타지")
        );

        // when
        when(genreRepository.findAll()).thenReturn(genres);

        // then
        List<GenreResponse> foundAllGenres = genreService.findAllGenres();

        Assertions.assertThat(foundAllGenres).isNotNull();
        Assertions.assertThat(foundAllGenres).hasSize(3);
        Assertions.assertThat(foundAllGenres.get(0).getGenreName()).isEqualTo("무협");

    }

    @DisplayName("새로운 장르 저장 성공")
    @Test
    void saveGenreType() {
        // given
        final String type = "무협";
        Genre genre = new Genre(type);
        when(genreRepository.findByName(type)).thenReturn(Optional.empty());

        // when
        genreService.saveGenreType(type);

        // then
        verify(genreRepository, times(1)).save(any(Genre.class));
    }

    @DisplayName("중복된 장르 저장 요청")
    @Test
    void failGenreType() {
        // given
        final String duplicateType = "중복";

        // when
        when(genreRepository.findByName(duplicateType)).thenReturn(
            Optional.of(new Genre(duplicateType)));

        // then
        assertThrows(CustomException.class, () -> {
            genreService.saveGenreType(duplicateType);
        });

    }
}