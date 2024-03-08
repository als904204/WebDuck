package com.example.webduck.webtoon.repository;

import com.example.webduck.webtoon.dto.WebtoonGenreResponse;
import com.example.webduck.webtoon.dto.WebtoonPopularResponse;
import com.example.webduck.webtoon.entity.WebtoonSortCondition;
import java.util.List;

public interface WebtoonGenreCustom {

    List<WebtoonGenreResponse> findWebtoonsByGenres(List<String> genreNames);

    // 리뷰 평점순 OR 리뷰개수순 정렬
    List<WebtoonPopularResponse> findPopularWebtoonsByCondition(WebtoonSortCondition condition);

}
