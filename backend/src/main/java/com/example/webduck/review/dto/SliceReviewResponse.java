package com.example.webduck.review.dto;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class SliceReviewResponse {

    private final Long reviewId;
    private final String content;
    private final String reviewerNickname;
    private final Long authorId;
    private final Integer rating;
    private final LocalDateTime createdAt;
    private final int likesCount;

    public SliceReviewResponse(Long reviewId, String content, String reviewerNickname,
        Long authorId, Integer rating, LocalDateTime createdAt,int likesCount) {
        this.reviewId = reviewId;
        this.content = content;
        this.reviewerNickname = reviewerNickname;
        this.authorId = authorId;
        this.rating = rating;
        this.createdAt = createdAt;
        this.likesCount = likesCount;
    }

}
