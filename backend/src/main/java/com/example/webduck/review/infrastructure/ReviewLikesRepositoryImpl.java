package com.example.webduck.review.infrastructure;

import com.example.webduck.review.domain.ReviewLikes;
import com.example.webduck.review.service.port.ReviewLikesRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@RequiredArgsConstructor
@Repository
public class ReviewLikesRepositoryImpl implements ReviewLikesRepository {

    private final ReviewLikesJpaRepository reviewLikesJpaRepository;

    @Override
    public ReviewLikes save(ReviewLikes reviewLikes) {
        ReviewLikesEntity entity = reviewLikesJpaRepository.save(ReviewLikesEntity.from(reviewLikes));
        return entity.toModel();
    }

    @Override
    public Optional<ReviewLikes> findByReviewIdAndMemberId(Long reviewId, Long memberId) {
        Optional<ReviewLikesEntity> entity = reviewLikesJpaRepository.findByReviewIdAndMemberId(
            reviewId, memberId);
        return entity.map(ReviewLikesEntity::toModel);
    }

    @Override
    public void deleteById(Long id) {
        reviewLikesJpaRepository.deleteById(id);
    }

}
