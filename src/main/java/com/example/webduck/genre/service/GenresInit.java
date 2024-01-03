package com.example.webduck.genre.service;

import com.example.webduck.genre.entity.Genre;
import com.example.webduck.genre.repository.GenreRepository;
import jakarta.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class GenresInit {

    private final GenreRepository genreRepository;

    private static final String ROMANCE = "로맨스";
    private static final String FANTASY = "판타지";
    private static final String GAG = "개그";
    private static final String MARTIAL_ARTS = "무협";
    private static final String ADULT = "성인";

    // 기본 장르 DB INSERT
    @PostConstruct
    public void initGenres() {
        List<String> initGenres = Arrays.asList(
            ROMANCE,
            FANTASY,
            GAG,
            MARTIAL_ARTS,
            ADULT
        );

        initGenres.forEach(genre ->
            genreRepository.findByType(genre)
            .orElseGet(() -> genreRepository.save(new Genre(genre))));

    }

}
