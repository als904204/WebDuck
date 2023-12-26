package com.example.webduck.Webtoon.repository;

import com.example.webduck.Webtoon.entity.Webtoon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebtoonRepository extends JpaRepository<Webtoon,Long> {

}
