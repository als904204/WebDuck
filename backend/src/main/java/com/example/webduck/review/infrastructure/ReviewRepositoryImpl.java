package com.example.webduck.review.infrastructure;

import com.example.webduck.global.common.SliceResponse;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.review.controller.response.ReviewSliceResponse;
import com.example.webduck.review.domain.Review;
import com.example.webduck.review.service.port.ReviewRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewJpaRepository reviewJpaRepository;

    @Override
    public Review save(Review review) {
        ReviewEntity entity = reviewJpaRepository.save(ReviewEntity.from(review));
        return entity.toModel();
    }

    @Override
    public Optional<Review> findById(Long id) {
        Optional<ReviewEntity> entity = reviewJpaRepository.findById(id);
        return entity.map(ReviewEntity::toModel);
    }

    @Override
    public Review getById(Long id) {
        return findById(id).orElseThrow(
            () -> new CustomException(LogicExceptionCode.REVIEW_NOT_FOUND));
    }

    @Override
    public List<Review> findReviewsByWebtoonIdOrderByCreatedAtDesc(Long webtoonId) {
        List<ReviewEntity> entities = reviewJpaRepository.findReviewsByWebtoonIdOrderByCreatedAtDesc(
            webtoonId);

        return entities.stream()
            .map(ReviewEntity::toModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<Review> findReviewsByWebtoonId(Long webtoonId) {
        List<ReviewEntity> entities = reviewJpaRepository.findReviewsByWebtoonId(
            webtoonId);

        return entities.stream()
            .map(ReviewEntity::toModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<Review> findByMemberId(Long memberId) {
        List<ReviewEntity> entities = reviewJpaRepository.findByMemberId(memberId);
        return entities.stream()
            .map(ReviewEntity::toModel)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        reviewJpaRepository.deleteById(id);
    }

    @Override
    public int countReviewsByWebtoonId(Long webtoonId) {
        return reviewJpaRepository.countReviewsByWebtoonId(webtoonId);
    }

    @Override
    public boolean existsById(Long id) {
        return reviewJpaRepository.existsById(id);
    }

    @Override
    public SliceResponse<ReviewSliceResponse> findSliceReviews(Long webtoonId, Long nextReviewId,
        Pageable pageable) {
        return reviewJpaRepository.findSliceReviews(webtoonId, nextReviewId, pageable);
    }


}
