package com.example.webduck.review.service;

import com.example.webduck.config.security.oauth.entity.SessionMember;
import com.example.webduck.global.common.SliceResponse;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.review.dto.ReviewRequest;
import com.example.webduck.review.dto.ReviewResponse.ReviewAvg;
import com.example.webduck.review.dto.ReviewResponse.ReviewCount;
import com.example.webduck.review.dto.ReviewResponse.ReviewId;
import com.example.webduck.review.dto.SliceReviewResponse;
import com.example.webduck.review.entity.Review;
import com.example.webduck.review.repository.ReviewRepository;
import com.example.webduck.webtoon.entity.Webtoon;
import com.example.webduck.webtoon.repository.WebtoonRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final WebtoonRepository webtoonRepository;


    @Transactional
    public ReviewId saveReview(SessionMember sessionMember, ReviewRequest reviewRequest) {
        Long webtoonId = reviewRequest.getWebtoonId();

        Webtoon webtoon = webtoonRepository.findById(webtoonId)
            .orElseThrow(() -> new CustomException(
                LogicExceptionCode.WEBTOON_NOT_FOUND));

        // todo : 닉네임으로 변경
        Long memberId = sessionMember.getId();
        String email = sessionMember.getUsername();
        String content = reviewRequest.getContent();
        Integer rating = reviewRequest.getRating();

        Review review = Review.builder()
            .webtoonId(webtoonId)
            .memberId(memberId)
            .reviewerNickname(email)
            .content(content)
            .rating(rating)
            .build();

        // 리뷰 저장 시, 해당 웹툰 리뷰 개수를 증가한다.
        webtoon.incrementReviewCount();
        review = reviewRepository.save(review);
        return new ReviewId(review.getId());
    }

    // 리뷰 점수평균을 구한다
    @Transactional(readOnly = true)
    public ReviewAvg getReviewAvg(Long webtoonId) {
        List<Review> reviews = reviewRepository.findReviewsByWebtoonId(webtoonId);
        return new ReviewAvg(Review.calculateRatingAvg(reviews));
    }

    @Transactional(readOnly = true)
    public ReviewCount getReviewCount(Long webtoonId) {
        int size = reviewRepository.findReviewsByWebtoonId(webtoonId).size();
        return new ReviewCount(size);
    }

    @Transactional(readOnly = true)
    public SliceResponse<SliceReviewResponse> findReviewsByWebtoonId(Long webtoonId,
        Long nextReviewId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        if (nextReviewId != null) {
            if (!reviewRepository.existsById(nextReviewId)) {
                log.warn("not exists nextReviewId={}",nextReviewId);
                throw new CustomException(LogicExceptionCode.REVIEW_NOT_FOUND);
            }
        }

        webtoonIsExists(webtoonId);
        return reviewRepository.findSliceReviews(webtoonId, nextReviewId, pageable);
    }

    private void webtoonIsExists(Long id) {
        boolean webtoonIsExists = webtoonRepository.existsById(id);
        if (!webtoonIsExists) {
            log.warn("not exists webtoon={}",id);
            throw new CustomException(LogicExceptionCode.WEBTOON_NOT_FOUND);
        }
    }

}
