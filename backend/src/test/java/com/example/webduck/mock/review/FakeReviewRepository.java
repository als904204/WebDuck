package com.example.webduck.mock.review;

import com.example.webduck.global.common.SliceResponse;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.review.controller.response.ReviewSliceResponse;
import com.example.webduck.review.domain.Review;
import com.example.webduck.review.service.port.ReviewRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.data.domain.Pageable;

public class FakeReviewRepository implements ReviewRepository {

    private final AtomicLong autoGeneratedId = new AtomicLong(0);
    private final List<Review> data = Collections.synchronizedList(new ArrayList<>());



    @Override
    public Review save(Review review) {
        if (review.getId() == null || review.getId() == 0) {
            Review newReview = Review.builder()
                .id(autoGeneratedId.incrementAndGet())
                .webtoonId(review.getWebtoonId())
                .memberId(review.getMemberId())
                .reviewerNickname(review.getReviewerNickname())
                .content(review.getContent())
                .rating(review.getRating())
                .build();

            data.add(newReview);
            return newReview;
        } else {
            data.removeIf(item -> Objects.equals(item.getId(), review.getId()));
            data.add(review);
            return review;
        }
    }

    @Override
    public Optional<Review> findById(Long id) {
        return data.stream().filter(
            review -> review.getId().equals(id)
        ).findAny();
    }

    @Override
    public Review getById(Long id) {
        return findById(id).orElseThrow(
            () -> new CustomException(LogicExceptionCode.REVIEW_NOT_FOUND));
    }

    @Override
    public List<Review> findReviewsByWebtoonIdOrderByCreatedAtDesc(Long webtoonId) {
        List<Review> reviews = new ArrayList<>();

        for (Review review : data) {
            if (Objects.equals(review.getWebtoonId(), webtoonId)) {
                reviews.add(review);
            }
        }

        reviews.sort(Comparator.comparing(Review::getCreatedAt).reversed());
        return reviews;
    }

    @Override
    public List<Review> findReviewsByWebtoonId(Long webtoonId) {
        List<Review> reviews = new ArrayList<>();
        for (Review review : data) {
            if (Objects.equals(review.getWebtoonId(), webtoonId)) {
                reviews.add(review);
            }
        }
        return reviews;
    }

    @Override
    public List<Review> findByMemberId(Long memberId) {
        List<Review> reviews = new ArrayList<>();
        for (Review review : data) {
            if (Objects.equals(review.getMemberId(), memberId)) {
                reviews.add(review);
            }
        }
        return reviews;
    }


    @Override
    public void deleteById(Long id) {
        data.removeIf(review -> review.getId().equals(id));
    }

    @Override
    public int countReviewsByWebtoonId(Long webtoonId) {
        return (int) data.stream()
            .filter(review -> review.getWebtoonId().equals(webtoonId))
            .count();
    }

    @Override
    public boolean existsById(Long id) {
        return data.stream().anyMatch(review -> review.getId().equals(id));
    }

    // TODO
    @Override
    public SliceResponse<ReviewSliceResponse> findSliceReviews(Long webtoonId, Long nextReviewId,
        Pageable pageable) {
        return null;
    }
}
