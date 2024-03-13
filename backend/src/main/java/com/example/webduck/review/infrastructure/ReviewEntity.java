package com.example.webduck.review.infrastructure;

import com.example.webduck.global.common.BaseTime;
import com.example.webduck.review.domain.Review;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;
import javax.persistence.Table;
import lombok.Builder;


@Table(name = "review")
@Entity
public class ReviewEntity extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 웹툰 ID
    @Column(nullable = false)
    private Long webtoonId;

    // 작성자 ID
    @Column(nullable = false)
    private Long memberId;

    // 작성자 nickname
    @Column(nullable = false)
    private String reviewerNickname;

    // 리뷰 내용
    @Column(nullable = false)
    private String content;

    // 리뷰 점수
    @Column(nullable = false)
    private Integer rating;

    // 해당 리뷰 좋아요 수
    private int likesCount = 0;

    // todo : 세션맴버에서 닉네임 추가하고 리뷰 생성할 때 닉네임으로 설정
    @Builder
    public ReviewEntity(Long id, Long webtoonId, Long memberId, String reviewerNickname, String content,
        Integer rating,int likesCount) {
        this.id = id;
        this.webtoonId = webtoonId;
        this.memberId = memberId;
        this.reviewerNickname = reviewerNickname;
        this.content = content;
        this.rating = rating;
        this.likesCount = likesCount;
    }

    protected ReviewEntity() {}

    // Domain to Entity
    public static ReviewEntity from(Review review) {
        return ReviewEntity.builder()
            .id(review.getId())
            .webtoonId(review.getWebtoonId())
            .memberId(review.getMemberId())
            .reviewerNickname(review.getReviewerNickname())
            .content(review.getContent())
            .rating(review.getRating())
            .likesCount(review.getLikesCount())
            .build();
    }


    // Entity to Domain
    public Review toModel() {
        return Review.builder()
            .id(id)
            .webtoonId(webtoonId)
            .memberId(memberId)
            .likesCount(likesCount)
            .reviewerNickname(reviewerNickname)
            .content(content)
            .rating(rating)
            .build();
    }

    public Long getId() {
        return id;
    }
    public Long getWebtoonId() {
        return webtoonId;
    }
    public Long getMemberId() {
        return memberId;
    }
    public String getContent() {
        return content;
    }
    public String getReviewerNickname() {
        return reviewerNickname;
    }
    public Integer getRating() {
        return rating;
    }
    public int getLikesCount(){
        return likesCount;
    }


}

