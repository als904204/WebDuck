package com.example.webduck.Webtoon.repository;

import com.example.webduck.Webtoon.entity.PublishDay;
import com.example.webduck.Webtoon.entity.Webtoon;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebtoonRepository extends JpaRepository<Webtoon, Long> {

    List<Webtoon> findWebtoonByPublishDay(PublishDay publishDay);


}
