package com.example.webduck.webtoon.service.port;

import com.example.webduck.webtoon.domain.Webtoon;
import com.example.webduck.webtoon.controller.response.WebtoonGenreResponse;
import com.example.webduck.webtoon.controller.response.WebtoonPopularResponse;
import com.example.webduck.webtoon.infrastructure.Platform;
import com.example.webduck.webtoon.infrastructure.PublishDay;
import com.example.webduck.webtoon.infrastructure.WebtoonSortCondition;
import java.util.List;
import java.util.Optional;

public interface WebtoonRepository {

    Optional<Webtoon> findById(Long Id);

    Webtoon getById(Long id);
    List<Webtoon> findWebtoonsByPublishDay(PublishDay publishDay);

    List<Webtoon> findWebtoonsByPlatform(Platform platform);

    // ID 값들로 조회
    List<Webtoon> findAllByIdIn(List<Long> webtoonIds);

    List<Webtoon> findAll();

    List<WebtoonGenreResponse> findWebtoonsByGenres(List<String> genreNames);

    List<WebtoonPopularResponse> findPopularWebtoonsByCondition(WebtoonSortCondition condition);

    Webtoon save(Webtoon webtoon);

    boolean existsById(Long id);
}
