package com.example.webduck.review.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;


@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long webtoonId;

    @Column(nullable = false)
    private String reviewerNickname;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false,length = 255)
    private String content;

    // todo : 세션맴버에서 닉네임 추가하고 리뷰 생성할 때 닉네임으로 설정
    public Review(Long webtoonId, Long memberId,String reviewerNickname ,String content) {
        this.webtoonId = webtoonId;
        this.memberId = memberId;
        this.reviewerNickname = reviewerNickname;
        this.content = content;
    }

    protected Review() {}

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
}

