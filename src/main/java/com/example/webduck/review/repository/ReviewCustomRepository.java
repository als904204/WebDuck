package com.example.webduck.review.repository;

import com.example.webduck.global.common.SliceResponse;
import com.example.webduck.review.dto.SliceReviewResponse;
import org.springframework.data.domain.Pageable;

public interface ReviewCustomRepository {
    SliceResponse<SliceReviewResponse> findSliceReviews(Long webtoonId,Long nextIdReq, Pageable pageable);
}
