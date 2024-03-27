package com.example.webduck.review.service;

import com.example.webduck.global.security.oauth.entity.SessionMember;
import com.example.webduck.review.controller.port.ReviewLikesService;
import com.example.webduck.review.domain.Review;
import com.example.webduck.review.domain.ReviewLikes;
import com.example.webduck.review.service.port.ReviewLikesRepository;
import com.example.webduck.review.service.port.ReviewRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReviewLikesServiceImpl implements ReviewLikesService {

    private final ReviewRepository reviewRepository;
    private final ReviewLikesRepository reviewLikesRepository;




    @Transactional
    public Review updateLikes(Long reviewId, SessionMember sessionMember) {

        Review review = reviewRepository.getById(reviewId);

        Long memberId = sessionMember.getId();

        Optional<ReviewLikes> existingLikes = reviewLikesRepository.findByReviewIdAndMemberId(
            reviewId, memberId);

        // 이미 좋아요가 있을 경우 삭제 후 취소
        existingLikes.ifPresentOrElse(reviewLikes -> {
            reviewLikesRepository.deleteById(reviewLikes.getId());
            review.decreaseLikes();
        }, () -> {

            // 좋아요 안했을 경우 저장 후 증가
            ReviewLikes newReviewLikes = ReviewLikes.builder()
                .reviewId(reviewId)
                .memberId(memberId)
                .build();

            reviewLikesRepository.save(newReviewLikes);
            review.increaseLikes();

        });

        // 변경된 Review Domain 좋아요 값 DB 저장
        reviewRepository.save(review);

        return review;
    }

}
