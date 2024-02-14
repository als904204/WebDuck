package com.example.webduck.review.dto;


import lombok.Getter;

public class ReviewResponse {

    public record ReviewId(Long reviewId) {}
    public record ReviewAvg(Double rating) { }
    public record ReviewCount(int count) { }
    public record ReviewLikesResponse(String message, int likesCount) { }



}


