package com.example.webduck.webtoon.repository;

import com.example.webduck.webtoon.dto.WebtoonGenreResponse;
import com.example.webduck.webtoon.dto.WebtoonSortCondition.WebtoonConditionResponse;
import java.util.List;

public interface WebtoonGenreCustom {

    List<WebtoonGenreResponse> findWebtoonsByGenres(List<String> genreNames);

    List<WebtoonConditionResponse> findPopularWebtoonsByCondition(String condition);

}
