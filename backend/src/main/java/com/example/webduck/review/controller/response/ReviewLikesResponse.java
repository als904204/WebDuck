package com.example.webduck.review.controller.response;

import com.example.webduck.review.domain.Review;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewLikesResponse {

    private int likesCount;

    public static ReviewLikesResponse from(Review review) {
        return ReviewLikesResponse.builder()
            .likesCount(review.getLikesCount())
            .build();
    }

}