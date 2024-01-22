package com.example.webduck.review.dto;

import com.example.webduck.review.entity.Review;

public class ReviewResponse {

    private final String content;
    private final String reviewerNickname;
    private final Long authorId;

    private final Integer rating;

    public ReviewResponse(Review review) {
        this.content = review.getContent();
        this.authorId = review.getMemberId();
        this.reviewerNickname = review.getReviewerNickname();
        this.rating = review.getRating();
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

    public Integer getRating() {
        return rating;
    }

    public record ReviewId(Long reviewId) {}

    public record ReviewAvg(Double rating) { }
    public record ReviewCount(int count) { }


}


