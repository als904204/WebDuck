package com.example.webduck.genre.service;

import com.example.webduck.genre.entity.Genre;
import com.example.webduck.genre.repository.GenreRepository;

import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"dev", "test"}) // 개발 및 테스트 환경에서만 실행
@RequiredArgsConstructor
@Component
public class GenresInit {

    private final GenreRepository genreRepository;

    private static final String ROMANCE = "ROMANCE"; // 로맨스
    private static final String FANTASY = "FANTASY"; // 판타지
    private static final String GAG = "GAG"; // 개그
    private static final String MARTIAL_ARTS = "MARTIALARTS"; // 무협
    private static final String ACTION = "ACTION"; // 액션
    private static final String DAILY_LIFE = "DAILYLIFE"; // 일상
    private static final String THRILLER = "THRILLER"; // 스릴러
    private static final String DRAMA = "DRAMA"; // 드라마
    private static final String SPORTS = "SPORTS"; // 스포츠


    // TODO : 서버 시작할때 마다 모든 장르 하나하나 쿼리 함, 한번에 쿼리하는 방법 없나?
    // 기본 장르 DB INSERT
    @PostConstruct
    public void initGenres() {
        List<String> initGenres = Arrays.asList(
            ROMANCE,
            FANTASY,
            GAG,
            ACTION,
            DAILY_LIFE,
            THRILLER,
            MARTIAL_ARTS,
            DRAMA,
            SPORTS
        );

        initGenres.forEach(genre ->
            genreRepository.findByName(genre)
                .orElseGet(() -> genreRepository.save(new Genre(genre))));
    }
}