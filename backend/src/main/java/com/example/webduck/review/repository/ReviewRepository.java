package com.example.webduck.review.repository;

import com.example.webduck.review.entity.Review;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>,ReviewCustomRepository {

    List<Review> findReviewsByWebtoonIdOrderByCreatedAtDesc(Long webtoonId);

    List<Review> findReviewsByWebtoonId(Long webtoonId);

    List<Review> findByMemberId(Long memberId);

}
