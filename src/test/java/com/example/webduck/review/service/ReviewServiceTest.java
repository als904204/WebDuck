package com.example.webduck.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.example.webduck.global.exception.CustomException;
import com.example.webduck.review.entity.Review;
import com.example.webduck.review.repository.ReviewRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    List<Review> reviews;

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

    @DisplayName("리뷰점수 평균 계산 서비스 성공")
    @Test
    void calculateReviewAvg_success() {
        Long webtoonId = 1L;
        when(reviewRepository.findReviewsByWebtoonId(webtoonId)).thenReturn(reviews);
        Double avgRating = reviewService.getReviewAvg(webtoonId);
        assertThat(avgRating).isEqualTo(4.5);
    }

    @DisplayName("리뷰점수 평균 계산 서비스 잘못된 계산 검증")
    @Test
    void calculateReviewAvg_fail() {
        Long webtoonId = 1L;
        when(reviewRepository.findReviewsByWebtoonId(webtoonId)).thenReturn(reviews);
        Double avgRating = reviewService.getReviewAvg(webtoonId);
        assertThat(avgRating).isNotEqualTo(4);
    }

    @DisplayName("존재하지 않는 웹툰ID로 리뷰목록 조회시 예외 ")
    @Test
    void invalidWebtoonId_fail() {
        Long invalidWebtoonId = 100L;
        when(reviewRepository.findReviewsByWebtoonId(invalidWebtoonId)).thenReturn(
            Collections.emptyList());
        assertThrows(CustomException.class, () -> reviewService.getReviewAvg(invalidWebtoonId));
    }

}