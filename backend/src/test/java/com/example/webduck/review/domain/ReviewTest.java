package com.example.webduck.review.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.example.webduck.member.domain.Member;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReviewTest {

    @Test
    void validateAuthor() {
        Review review = Review.builder()
            .id(1L)
            .memberId(1L)
            .build();

        Member member = Member.builder()
            .id(1L)
            .build();

        assertDoesNotThrow(() -> Review.validateAuthor(review, member));
    }


    @Test
    void validateAuthorThrown() {
        Review review = Review.builder()
            .id(1L)
            .memberId(1L)
            .build();

        Member member = Member.builder()
            .id(9999L)
            .build();

        assertThatThrownBy(() -> {
            Review.validateAuthor(review, member);
        } );

    }

    @Test
    void calculateRatingAvg() {
        Review review1 = Review.builder()
            .id(1L)
            .rating(5)
            .memberId(1L)
            .build();

        Review review2 = Review.builder()
            .id(1L)
            .rating(5)
            .memberId(1L)
            .build();

        List<Review> reviews = List.of(review1, review2);

        Double ratingAvg = Review.calculateRatingAvg(reviews);

        assertThat(ratingAvg).isEqualTo(5.0);
    }

    @Test
    void calculateRatingAvg_zero() {
        Review review1 = Review.builder()
            .id(1L)
            .rating(0)
            .memberId(1L)
            .build();

        List<Review> reviews = List.of(review1);

        Double ratingAvg = Review.calculateRatingAvg(reviews);

        assertThat(ratingAvg).isEqualTo(0.0);
    }

    @Test
    void upLikesCount() {
        Review review = Review.builder()
            .id(1L)
            .likesCount(99)
            .rating(0)
            .memberId(1L)
            .build();

        review.upLikesCount();

        assertThat(review.getLikesCount()).isEqualTo(100);
    }

    @Test
    void downLikesCount() {
        Review review = Review.builder()
            .id(1L)
            .likesCount(100)
            .rating(0)
            .memberId(1L)
            .build();

        review.downLikesCount();

        assertThat(review.getLikesCount()).isEqualTo(99);
    }
}