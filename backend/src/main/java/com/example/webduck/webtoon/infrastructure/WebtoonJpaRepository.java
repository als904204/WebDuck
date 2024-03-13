package com.example.webduck.webtoon.infrastructure;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebtoonJpaRepository extends JpaRepository<WebtoonEntity, Long>,WebtoonGenreCustom{

    List<WebtoonEntity> findWebtoonsByPublishDay(PublishDay publishDay);

    List<WebtoonEntity> findWebtoonsByPlatform(Platform platform);

    // ID 값들로 조회
    List<WebtoonEntity> findAllByIdIn(List<Long> webtoonIds);


}
