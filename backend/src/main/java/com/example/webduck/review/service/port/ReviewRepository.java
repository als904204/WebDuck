package com.example.webduck.review.service.port;

import com.example.webduck.global.common.SliceResponse;
import com.example.webduck.review.controller.response.ReviewSliceResponse;
import com.example.webduck.review.domain.Review;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface ReviewRepository {

    Review save(Review review);

    Optional<Review> findById(Long id);

    Review getById(Long id);

    List<Review> findReviewsByWebtoonIdOrderByCreatedAtDesc(Long webtoonId);

    List<Review> findReviewsByWebtoonId(Long webtoonId);

    List<Review> findByMemberId(Long memberId);

    void deleteById(Long id);

    int countReviewsByWebtoonId(Long webtoonId);

    boolean existsById(Long id);

    SliceResponse<ReviewSliceResponse> findSliceReviews(Long webtoonId, Long nextReviewId, Pageable pageable);
}
