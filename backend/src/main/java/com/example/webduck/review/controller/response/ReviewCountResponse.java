package com.example.webduck.review.controller.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ReviewCountResponse {

    private final int count;

    public static ReviewCountResponse from(int count) {
        return ReviewCountResponse.builder()
            .count(count)
            .build();
    }
}
