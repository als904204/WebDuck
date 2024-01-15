package com.example.webduck.review.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.webduck.config.ConverterConfigTest;
import com.example.webduck.config.QueryDslConfigTest;
import com.example.webduck.review.entity.Review;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Import({QueryDslConfigTest.class, ConverterConfigTest.class})
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;
    private final String firstReview = "hello Review1";
    private final String lastReview = "hello Review2";



    @BeforeEach
    void setUp() {
        List<Review> reviews = Arrays.asList(
            Review.builder()
                .webtoonId(1L)
                .memberId(1L)
                .reviewerNickname("nickname")
                .content(firstReview)
                .rating(1)
                .build(),
            Review.builder()
                .webtoonId(1L)
                .memberId(1L)
                .reviewerNickname("nickname")
                .content(lastReview)
                .rating(1)
                .build()
        );
        reviewRepository.saveAll(reviews);
    }

    @DisplayName("리뷰 최근순 정렬(웹툰ID로 리뷰 찾기)")
    @Test
    void findReviewsByWebtoonIdOrderByCreatedAtDesc() {
        // given
        Long webtoonId = 1L;

        List<Review> reviews = reviewRepository.findReviewsByWebtoonIdOrderByCreatedAtDesc(
            webtoonId);

        assertThat(reviews.get(0).getContent()).isEqualTo(lastReview);
        assertThat(reviews.get(1).getContent()).isEqualTo(firstReview);

    }
}