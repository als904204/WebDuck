package com.example.webduck.webtoon.repository;

import com.example.webduck.webtoon.entity.Platform;
import com.example.webduck.webtoon.entity.PublishDay;
import com.example.webduck.webtoon.entity.Webtoon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WebtoonRepository extends JpaRepository<Webtoon, Long>,WebtoonGenreCustom{

    List<Webtoon> findWebtoonByPublishDay(PublishDay publishDay);

    List<Webtoon> findWebtoonByPlatform(Platform platform);

    // 단건 장르에 해당하는 웹툰 찾기
    // TODO : 페이징 적용된다면 QueryDSL 로 변경해야 함
    @Query("SELECT w "
        + "FROM Webtoon w "
        + "INNER JOIN WebtoonGenre wg ON wg.webtoon.id = w.id "
        + "INNER JOIN Genre g ON wg.genre.id = g.id "
        + "WHERE g.name = :name")
    List<Webtoon> findByWebtoonsGenreName(@Param("name") String name);
}
