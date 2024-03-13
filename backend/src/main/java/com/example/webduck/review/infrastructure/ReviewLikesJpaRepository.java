package com.example.webduck.review.infrastructure;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewLikesJpaRepository extends JpaRepository<ReviewLikesEntity, Long> {

    boolean existsByReviewIdAndMemberId(Long reviewId, Long memberId);

    void deleteByReviewIdAndMemberId(Long reviewId, Long memberId);

    Optional<ReviewLikesEntity> findByReviewIdAndMemberId(Long reviewId, Long memberId);

}
