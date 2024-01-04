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

    private static final String ROMANCE = "ROMANCE";
    private static final String FANTASY = "FANTASY";
    private static final String GAG = "GAG";
    private static final String MARTIAL_ARTS = "MARTIALARTS";
    private static final String ADULT = "ADULT";

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
            genreRepository.findByName(genre)
            .orElseGet(() -> genreRepository.save(new Genre(genre))));

    }

}
