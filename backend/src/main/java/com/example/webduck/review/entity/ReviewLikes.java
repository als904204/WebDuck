package com.example.webduck.review.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;

@Entity
public class ReviewLikes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "review_id")
    private Long reviewId;

    @Column(name = "member_Id")
    private Long memberId;

    @Builder
    public ReviewLikes(Long reviewId, Long memberId) {
        this.reviewId = reviewId;
        this.memberId = memberId;
    }

    protected ReviewLikes(){}

}
