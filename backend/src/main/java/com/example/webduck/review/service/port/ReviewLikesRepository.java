package com.example.webduck.review.service.port;

import com.example.webduck.review.domain.ReviewLikes;
import java.util.Optional;

public interface ReviewLikesRepository {

    ReviewLikes save(ReviewLikes reviewLikes);

    Optional<ReviewLikes> findByReviewIdAndMemberId(Long reviewId, Long memberId);

    void deleteById(Long id);
}
