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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final WebtoonRepository webtoonRepository;


    @Transactional
    public Long saveReview(SessionMember sessionMember, ReviewRequest reviewRequest) {

        Long webtoonId = reviewRequest.getWebtoonId();
        webtoonIsExists(webtoonId);

        Long memberId = sessionMember.getId();
        // todo : 닉네임으로 변경
        String email = sessionMember.getEmail();
        String content = reviewRequest.getContent();


        Review review = new Review(webtoonId,memberId,email,content);
        review = reviewRepository.save(review);
        return review.getId();
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
