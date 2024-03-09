package com.example.webduck.webtoon.repository;

import com.example.webduck.webtoon.entity.Platform;
import com.example.webduck.webtoon.entity.PublishDay;
import com.example.webduck.webtoon.entity.Webtoon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebtoonRepository extends JpaRepository<Webtoon, Long>,WebtoonGenreCustom{

    List<Webtoon> findWebtoonsByPublishDay(PublishDay publishDay);

    List<Webtoon> findWebtoonsByPlatform(Platform platform);

    // ID 값들로 조회
    List<Webtoon> findAllByIdIn(List<Long> webtoonIds);


}
