package com.example.webduck.webtoon.repository;

import com.example.webduck.webtoon.dto.WebtoonGenreResponse;
import java.util.List;

public interface WebtoonGenreCustom {
    List<WebtoonGenreResponse> findWebtoonsByGenres(List<String> genreNames);
}
