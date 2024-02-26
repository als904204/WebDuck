package com.example.webduck.review.repository;

import com.example.webduck.review.entity.Review;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>,ReviewCustomRepository {

    List<Review> findReviewsByWebtoonIdOrderByCreatedAtDesc(Long webtoonId);

    List<Review> findReviewsByWebtoonId(Long webtoonId);

    Optional<Review> findReviewByIdAndMemberId(Long reviewId, Long memberId);
}
