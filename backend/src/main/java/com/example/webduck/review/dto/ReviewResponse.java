package com.example.webduck.review.dto;


public class ReviewResponse {

    public record ReviewId(Long reviewId) {}
    public record ReviewAvg(Double rating) { }
    public record ReviewCount(int count) { }


}


