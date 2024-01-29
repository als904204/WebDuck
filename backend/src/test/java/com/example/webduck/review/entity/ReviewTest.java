package com.example.webduck.review.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class ReviewTest {

    private List<Review> reviews;


    @BeforeEach
    void setUp() {
        reviews = Arrays.asList(
            Review.builder()
                .webtoonId(1L)
                .memberId(1L)
                .reviewerNickname("user1")
                .content("review content1")
                .rating(5)
                .build(),
            Review.builder()
                .webtoonId(1L)
                .memberId(1L)
                .reviewerNickname("user1")
                .content("review content2")
                .rating(4)
                .build()
        );
    }

    @DisplayName("리뷰 평균점수 계산")
    @Test
    void calculateRatingAvg() {
        Double avgRating = Review.calculateRatingAvg(reviews);
        assertThat(avgRating).isEqualTo(4.5);
    }
}