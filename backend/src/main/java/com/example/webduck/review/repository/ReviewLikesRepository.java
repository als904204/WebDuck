package com.example.webduck.review.repository;

import com.example.webduck.review.entity.ReviewLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewLikesRepository extends JpaRepository<ReviewLikes, Long> {


    boolean existsByReviewIdAndMemberId(Long reviewId, Long memberId);


    void deleteByReviewIdAndMemberId(Long reviewId, Long memberId);


}
