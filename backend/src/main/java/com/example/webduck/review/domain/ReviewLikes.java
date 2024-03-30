package com.example.webduck.review.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewLikes {
    
    private Long id;
    private Long reviewId;
    private Long memberId;

    @Builder
    public ReviewLikes(Long id, Long reviewId, Long memberId) {
        this.id = id;
        this.reviewId = reviewId;
        this.memberId = memberId;
    }

    public static ReviewLikes from(Long reviewId, Long memberId) {
        return ReviewLikes.builder()
            .reviewId(reviewId)
            .memberId(memberId)
            .build();
    }

}
