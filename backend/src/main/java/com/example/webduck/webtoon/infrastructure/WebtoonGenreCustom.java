package com.example.webduck.webtoon.infrastructure;

import com.example.webduck.webtoon.controller.response.WebtoonGenreResponse;
import com.example.webduck.webtoon.controller.response.WebtoonPopularResponse;
import java.util.List;

public interface WebtoonGenreCustom {

    List<WebtoonGenreResponse> findWebtoonsByGenres(List<String> genreNames);

    // 리뷰 평점순 OR 리뷰개수순 정렬
    List<WebtoonPopularResponse> findPopularWebtoonsByCondition(WebtoonSortCondition condition);

}
