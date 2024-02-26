package com.example.webduck.review.service;

import com.example.webduck.config.security.oauth.entity.SessionMember;
import com.example.webduck.global.common.SliceResponse;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.member.repository.MemberRepository;
import com.example.webduck.review.dto.ReviewSave;
import com.example.webduck.review.dto.ReviewResponse.ReviewAvg;
import com.example.webduck.review.dto.ReviewResponse.ReviewCount;
import com.example.webduck.review.dto.ReviewResponse.ReviewId;
import com.example.webduck.review.dto.ReviewResponse.ReviewLikesResponse;
import com.example.webduck.review.dto.ReviewUpdate;
import com.example.webduck.review.dto.SliceReviewResponse;
import com.example.webduck.review.entity.Review;
import com.example.webduck.review.entity.ReviewLikes;
import com.example.webduck.review.repository.ReviewLikesRepository;
import com.example.webduck.review.repository.ReviewRepository;
import com.example.webduck.webtoon.entity.Webtoon;
import com.example.webduck.webtoon.repository.WebtoonRepository;
import java.util.ArrayList;
import java.util.List;
import javax.security.auth.login.LoginException;
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

    private final ReviewLikesRepository reviewLikesRepository;

    private final MemberRepository memberRepository;


    @Transactional
    public ReviewId saveReview(SessionMember sessionMember, ReviewSave reviewSave) {

        memberRepository.existsById(sessionMember.getId());

        Long webtoonId = reviewSave.getWebtoonId();

        Webtoon webtoon = webtoonRepository.findById(webtoonId)
            .orElseThrow(() -> new CustomException(
                LogicExceptionCode.WEBTOON_NOT_FOUND));

        Long memberId = sessionMember.getId();
        String username = sessionMember.getUsername();
        String content = reviewSave.getContent();
        Integer rating = reviewSave.getRating();

        Review review = Review.builder()
            .webtoonId(webtoonId)
            .memberId(memberId)
            .reviewerNickname(username)
            .content(content)
            .rating(rating)
            .build();

        // 리뷰 저장 시, 해당 웹툰 리뷰 개수를 증가한다.
        webtoon.incrementReviewCount();
        review = reviewRepository.save(review);
        log.info("m.pk={} is saved review(r.pk={}_", memberId, review.getId());
        return new ReviewId(review.getId());
    }

    @Transactional
    public void updateReview(Long reviewId, SessionMember sessionMember, ReviewUpdate request) {

        Long memberId = sessionMember.getId();

        memberRepository.existsById(memberId);

        Review review = reviewRepository.findReviewByIdAndMemberId(reviewId, memberId)
            .orElseThrow(() -> new CustomException(
                LogicExceptionCode.BAD_REQUEST));

        review.updateReview(request.getContent());

        log.info("review update success m.id={}, r.id={}, r.content={}", memberId,
            reviewId, request.getContent());
    }

    // 리뷰 평균 점수
    @Transactional(readOnly = true)
    public ReviewAvg getAvg(Long webtoonId) {

        List<Review> reviews = reviewRepository.findReviewsByWebtoonId(webtoonId);

        Double reviewAvg = Review.calculateRatingAvg(reviews);

        return new ReviewAvg(reviewAvg);
    }

    @Transactional(readOnly = true)
    public ReviewCount getCount(Long webtoonId) {
        int size = reviewRepository.findReviewsByWebtoonId(webtoonId).size();
        return new ReviewCount(size);
    }

    // 웹툰 ID 로 리뷰 목록 가져오기
    @Transactional(readOnly = true)
    public SliceResponse<SliceReviewResponse> findReviewsByWebtoonId(Long webtoonId,
        Long nextReviewId, int page, int size, SessionMember member) {

        Pageable pageable = PageRequest.of(page, size);

        if (nextReviewId != null) {
            if (!reviewRepository.existsById(nextReviewId)) {
                log.warn("not exists nextReviewId={}", nextReviewId);
                throw new CustomException(LogicExceptionCode.REVIEW_NOT_FOUND);
            }
        }

        webtoonIsExists(webtoonId);

        SliceResponse<SliceReviewResponse> reviewList = reviewRepository.findSliceReviews(
            webtoonId, nextReviewId, pageable);

        // 리뷰 작성자 여부
        if (member != null) {
            for (SliceReviewResponse response : reviewList.getItem()) {
                if (response.getAuthorId().equals(member.getId())) {
                    response.setAuthor();
                }
            }
        }


        return reviewList;
    }

    // 리뷰 좋아요 / 이미 있다면 좋아요 취소
    @Transactional
    public ReviewLikesResponse updateLikes(Long reviewId, SessionMember sessionMember) {
        Long memberId = sessionMember.getId();

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new CustomException(
            LogicExceptionCode.REVIEW_NOT_FOUND));

        boolean hasLikes = reviewLikesRepository.existsByReviewIdAndMemberId(reviewId, memberId);

        if (hasLikes) {
            reviewLikesRepository.deleteByReviewIdAndMemberId(reviewId, memberId);
            review.downLikesCount();
            int likesCount = review.getLikesCount();
            return new ReviewLikesResponse(true, likesCount);
        }else{
            ReviewLikes reviewLikes = ReviewLikes.builder()
                .reviewId(reviewId)
                .memberId(memberId)
                .build();

            reviewLikesRepository.save(reviewLikes);

            review.upLikesCount();
            int likesCount = review.getLikesCount();
            return new ReviewLikesResponse(true, likesCount);
        }
    }

    private void webtoonIsExists(Long id) {
        boolean webtoonIsExists = webtoonRepository.existsById(id);
        if (!webtoonIsExists) {
            log.warn("not exists webtoon={}",id);
            throw new CustomException(LogicExceptionCode.WEBTOON_NOT_FOUND);
        }
    }

}
