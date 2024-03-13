package com.example.webduck.review.controller.response;

import com.example.webduck.review.domain.Review;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewCreateResponse {

    private Long reviewId;

    public static ReviewCreateResponse from(Review review) {
        return ReviewCreateResponse.builder()
            .reviewId(review.getId())
            .build();
    }
}
