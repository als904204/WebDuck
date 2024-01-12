package com.example.webduck.review.dto;

import com.example.webduck.review.entity.Review;

public class ReviewResponse {

    private final String content;
    private final String reviewerNickname;
    private final Long authorId;
    public ReviewResponse(Review review) {
        this.content = review.getContent();
        this.authorId = review.getMemberId();
        this.reviewerNickname = review.getReviewerNickname();
    }

    public String getContent() {
        return content;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getReviewerNickname() {
        return reviewerNickname;
    }
}


