package com.example.webduck.review.infrastructure;


import com.example.webduck.global.common.BaseTime;
import com.example.webduck.review.domain.ReviewLikes;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;

@Table(name = "review_likes")
@Entity
public class ReviewLikesEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "member_Id")
    private Long memberId;

    @Builder
    public ReviewLikesEntity(Long reviewId, Long memberId) {
        this.reviewId = reviewId;
        this.memberId = memberId;
    }

    public static ReviewLikesEntity from(ReviewLikes reviewLikes) {
        return ReviewLikesEntity.builder()
            .reviewId(reviewLikes.getReviewId())
            .memberId(reviewLikes.getMemberId())
            .build();
    }

    public ReviewLikes toModel() {
        return ReviewLikes.builder()
            .id(id)
            .reviewId(reviewId)
            .memberId(memberId)
            .build();
    }
    protected ReviewLikesEntity(){}

}
