package com.example.webduck.review.entity;

import com.example.webduck.global.common.BaseTime;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.List;
import lombok.Builder;


@Entity
public class Review extends BaseTime {

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

    // todo : 세션맴버에서 닉네임 추가하고 리뷰 생성할 때 닉네임으로 설정
    @Builder
    public Review(Long webtoonId, Long memberId,String reviewerNickname ,String content,Integer rating) {
        this.webtoonId = webtoonId;
        this.memberId = memberId;
        this.reviewerNickname = reviewerNickname;
        this.content = content;
        this.rating = rating;
    }

    protected Review() {}

    // 리뷰점수 평균 구하기
    public static Double calculateRatingAvg(List<Review> reviews) {
        if (reviews == null || reviews.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        for (Review review : reviews) {
            sum += review.getRating();
        }
        double avg = (double) sum / reviews.size();

        return Math.round(avg * 10.0) / 10.0; // 반올림 후 리턴
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
}

