package com.example.webduck.review.dto;



public class ReviewResponse {

    public static class ReviewId {
        private final Long reviewId;

        public ReviewId(Long reviewId) {
            this.reviewId = reviewId;
        }

        public Long getReviewId() {
            return reviewId;
        }
    }

    public static class ReviewAvg {
        private final Double rating;

        public ReviewAvg(Double rating) {
            this.rating = rating;
        }

        public Double getRating() {
            return rating;
        }
    }

    public static class ReviewCount {
        private final int count;

        public ReviewCount(int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }
    }

    public static class ReviewLikesResponse {
        private final boolean success;
        private final int likesCount;

        public ReviewLikesResponse(boolean success, int likesCount) {
            this.success = success;
            this.likesCount = likesCount;
        }

        public boolean isSuccess() {
            return success;
        }

        public int getLikesCount() {
            return likesCount;
        }
    }
}



