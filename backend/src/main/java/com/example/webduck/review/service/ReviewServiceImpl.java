package com.example.webduck.review.service;

import com.example.webduck.global.security.oauth.entity.SessionMember;
import com.example.webduck.global.common.SliceResponse;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.member.domain.Member;
import com.example.webduck.member.service.port.MemberRepository;
import com.example.webduck.review.controller.port.ReviewService;
import com.example.webduck.review.domain.Review;
import com.example.webduck.review.domain.ReviewCreate;
import com.example.webduck.review.controller.response.ReviewSliceResponse;
import com.example.webduck.review.domain.ReviewLikes;
import com.example.webduck.review.service.port.ReviewRepository;
import com.example.webduck.webtoon.domain.Webtoon;
import com.example.webduck.webtoon.service.port.WebtoonRepository;
import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Builder
@Slf4j
@RequiredArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;

    private final WebtoonRepository webtoonRepository;

    private final MemberRepository memberRepository;


    @Override
    public Review findById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new CustomException(
            LogicExceptionCode.REVIEW_NOT_FOUND));
    }

    @Override
    @Transactional
    public Review create(SessionMember sessionMember, ReviewCreate reviewCreate) {

        Member member = memberRepository.getById(sessionMember.getId());
        Review review = Review.from(reviewCreate, member);


        review = reviewRepository.save(review);

        // 리뷰 저장 시,웹툰의 리뷰 개수를 증가한다
        Webtoon webtoon = webtoonRepository.getById(reviewCreate.getWebtoonId());
        webtoon.incrementReviewCount();
        webtoonRepository.save(webtoon);
        log.info("m.pk={} is saved review(r.pk={}_", member.getId(), review.getId());

        return review;
    }

    @Override
    public void deleteReview(SessionMember sessionMember,Long reviewId) {

        Member member = memberRepository.getById(sessionMember.getId());
        Review review = reviewRepository.getById(reviewId);

        Review.validateAuthor(review, member);

        reviewRepository.deleteById(review.getId());

        log.info("review delete! r.id={}", review.getId());
    }


    // 리뷰 평균 점수
    @Override
    @Transactional(readOnly = true)
    public Double getAvg(Long webtoonId) {
        List<Review> reviews = reviewRepository.findReviewsByWebtoonId(webtoonId);
        return Review.calculateRatingAvg(reviews);
    }

    // 리뷰 개수
    @Override
    @Transactional(readOnly = true)
    public int getCount(Long webtoonId) {
        return reviewRepository.countReviewsByWebtoonId(webtoonId);
    }

    // 웹툰 ID 로 리뷰 목록 가져오기
    @Override
    @Transactional(readOnly = true)
    public SliceResponse<ReviewSliceResponse> findReviewsByWebtoonId(Long webtoonId,
        Long nextReviewId, int page, int size, SessionMember member) {

        webtoonIsExists(webtoonId);

        Pageable pageable = PageRequest.of(page, size);


        if (nextReviewId != null) {
            if (!reviewRepository.existsById(nextReviewId)) {
                log.warn("not exists nextReviewId={}", nextReviewId);
                throw new CustomException(LogicExceptionCode.REVIEW_NOT_FOUND);
            }
        }

        SliceResponse<ReviewSliceResponse> reviewList = reviewRepository.findSliceReviews(webtoonId,
            nextReviewId, pageable);

        flagAuthorReviews(member, reviewList);

        return reviewList;
    }

    // 리뷰 좋아요
    @Transactional
    public Review updateLikes(Long reviewId, SessionMember sessionMember) {

        Review review = reviewRepository.getById(reviewId);

        Long memberId = sessionMember.getId();

        Optional<ReviewLikes> reviewLikes = reviewRepository.findReviewLikesByReviewIdAndMemberId(
            reviewId, memberId);

        // 이미 좋아요가 있을 경우 삭제 후 취소
        reviewLikes.ifPresentOrElse(existsLikes -> {
            reviewRepository.deleteReviewLikesById(existsLikes.getId());
            review.decreaseLikes();
        }, () -> {
            // 좋아요 안했을 경우 저장 후 증가
            ReviewLikes newReviewLikes = ReviewLikes.builder()
                .reviewId(reviewId)
                .memberId(memberId)
                .build();
            reviewRepository.saveReviewLikes(newReviewLikes);
            review.increaseLikes();
        });

        // 변경된 Review Domain 좋아요 값 DB 저장
        reviewRepository.save(review);

        return review;
    }


    // 리뷰 작성자 여부
    public void flagAuthorReviews(SessionMember member,
        SliceResponse<ReviewSliceResponse> reviewList) {
        if (member != null) {
            for (ReviewSliceResponse response : reviewList.getItem()) {
                if (response.getAuthorId().equals(member.getId())) {
                    response.setAuthor();
                }
            }
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
