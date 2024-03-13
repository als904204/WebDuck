package com.example.webduck.webtoon.controller.port;

import com.example.webduck.webtoon.controller.response.WebtoonDetails;
import com.example.webduck.webtoon.controller.response.WebtoonGenreResponse;
import com.example.webduck.webtoon.controller.response.WebtoonPopularResponse;
import com.example.webduck.webtoon.controller.response.WebtoonResponse;
import com.example.webduck.webtoon.infrastructure.Platform;
import com.example.webduck.webtoon.infrastructure.PublishDay;
import com.example.webduck.webtoon.infrastructure.WebtoonSortCondition;
import java.util.List;

public interface WebtoonService {

    WebtoonDetails getWebtoonDetails(Long webtoonId);

    List<WebtoonResponse> findAll();

    List<WebtoonResponse> findWebtoonsByPublishDay(PublishDay publishDay);

    List<WebtoonResponse> findWebtoonsByPlatform(Platform platform);

    List<WebtoonGenreResponse> findWebtoonsByGenreNames(List<String> genreNames);

    List<WebtoonPopularResponse> findPopularWebtoonsByCondition(WebtoonSortCondition condition);

}
