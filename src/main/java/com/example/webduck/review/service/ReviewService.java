package com.example.webduck.review.service;

import com.example.webduck.config.security.oauth.entity.SessionMember;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.review.dto.ReviewRequest;
import com.example.webduck.review.dto.ReviewResponse;
import com.example.webduck.review.entity.Review;
import com.example.webduck.review.repository.ReviewRepository;
import com.example.webduck.webtoon.repository.WebtoonRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final WebtoonRepository webtoonRepository;


    @Transactional
    public Long saveReview(SessionMember sessionMember, ReviewRequest reviewRequest) {

        Long webtoonId = reviewRequest.getWebtoonId();
        webtoonIsExists(webtoonId);

        // todo : 닉네임으로 변경
        Long memberId = sessionMember.getId();
        String email = sessionMember.getEmail();
        String content = reviewRequest.getContent();
        Integer rating = reviewRequest.getRating();

        Review review = Review.builder()
            .webtoonId(webtoonId)
            .memberId(memberId)
            .reviewerNickname(email)
            .content(content)
            .rating(rating)
            .build();

        review = reviewRepository.save(review);
        return review.getId();
    }

    // 리뷰 점수평균을 구한다
    @Transactional(readOnly = true)
    public Double getReviewAvg(Long webtoonId) {
        List<Review> reviews = reviewRepository.findReviewsByWebtoonId(webtoonId);
        return Review.calculateRatingAvg(reviews);
    }

    @Transactional(readOnly = true)
    public Integer getReviewCount(Long webtoonId) {
        return reviewRepository.findReviewsByWebtoonId(webtoonId).size();
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> getReviewsByWebtoonId(Long id) {
        webtoonIsExists(id);
        List<Review> reviews = reviewRepository.findReviewsByWebtoonIdOrderByCreatedAtDesc(id);
        return reviews.stream()
            .map(ReviewResponse::new)
            .toList();
    }

    private void webtoonIsExists(Long id) {
        boolean webtoonIsExists = webtoonRepository.existsById(id);
        if (!webtoonIsExists) {
            throw new CustomException(LogicExceptionCode.WEBTOON_NOT_FOUND);
        }
    }

}
