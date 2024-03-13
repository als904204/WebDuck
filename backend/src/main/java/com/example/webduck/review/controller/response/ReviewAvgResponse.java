package com.example.webduck.review.controller.response;

import com.example.webduck.review.domain.Review;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewAvgResponse {
    private final Double rating;

    public static ReviewAvgResponse from(Double rating) {
        return ReviewAvgResponse.builder()
            .rating(rating)
            .build();
    }
}
