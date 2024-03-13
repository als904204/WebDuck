package com.example.webduck.review.infrastructure;

import com.example.webduck.global.common.SliceResponse;
import com.example.webduck.review.controller.response.ReviewSliceResponse;
import org.springframework.data.domain.Pageable;

public interface ReviewCustomRepository {
    SliceResponse<ReviewSliceResponse> findSliceReviews(Long webtoonId,Long nextIdReq, Pageable pageable);
}
