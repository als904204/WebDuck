package com.example.webduck.review.infrastructure;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, Long>,ReviewCustomRepository {

    List<ReviewEntity> findReviewsByWebtoonIdOrderByCreatedAtDesc(Long webtoonId);

    List<ReviewEntity> findReviewsByWebtoonId(Long webtoonId);

    int countReviewsByWebtoonId(Long webtoonId);

    List<ReviewEntity> findByMemberId(Long memberId);

}
