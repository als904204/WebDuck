package com.example.webduck.genre.service;

import com.example.webduck.genre.dto.GenreResponse;
import com.example.webduck.genre.entity.Genre;
import com.example.webduck.genre.repository.GenreRepository;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class GenreService {

    private final GenreRepository genreRepository;

    // 모든 장르 찾기
    @Transactional
    public List<GenreResponse> findAllGenres() {
        List<Genre> genres = genreRepository.findAll();
        return genres.stream()
            .map(GenreResponse::new)
            .toList();
    }

    // 새로운 장르 생성
    @Transactional
    public void saveGenreType(String type) {

        // 중복 검증
        genreRepository.findByType(type).ifPresent(genre -> {
            throw new CustomException(LogicExceptionCode.DUPLICATE_REQUEST);
        });

        Genre genre = new Genre(type);
        genreRepository.save(genre);
    }

}
