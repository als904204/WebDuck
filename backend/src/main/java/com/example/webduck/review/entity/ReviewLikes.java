package com.example.webduck.review.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
