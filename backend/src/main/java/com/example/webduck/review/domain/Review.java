package com.example.webduck.review.domain;

import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.member.domain.Member;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public class Review {

    private static final Logger logger = LoggerFactory.getLogger(Review.class);


    private Long id;
    private Long webtoonId;
    private Long memberId;
    private String reviewerNickname;
    private String content;
    private Integer rating;
    private int likesCount;
    private LocalDateTime createdAt;

    @Builder
    public Review(Long id, Long webtoonId, Long memberId, String reviewerNickname, String content,
        Integer rating, int likesCount,LocalDateTime createdAt) {
        this.id = id;
        this.webtoonId = webtoonId;
        this.memberId = memberId;
        this.reviewerNickname = reviewerNickname;
        this.content = content;
        this.rating = rating;
        this.likesCount = likesCount;
        this.createdAt = createdAt;
    }

    public static Review from(ReviewCreate reviewCreate, Member member) {
        return Review.builder()
            .webtoonId(reviewCreate.getWebtoonId())
            .memberId(member.getId())
            .reviewerNickname(member.getUsername())
            .content(reviewCreate.getContent())
            .rating(reviewCreate.getRating())
            .build();
    }



    public static void validateAuthor(Review review, Member member) {
        Long authorId = review.getMemberId();
        Long memberId = member.getId();

        if (!authorId.equals(memberId)) {
            logger.error("Review author mismatch. m.id={}, r.authorId={}", memberId, authorId);
            throw new CustomException(LogicExceptionCode.BAD_REQUEST);
        }

    }

    public static Double calculateRatingAvg(List<Review> reviews) {
        if (reviews == null || reviews.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        for (Review reviewEntity : reviews) {
            sum += reviewEntity.getRating();
        }
        double avg = (double) sum / reviews.size();

        return Math.round(avg * 10.0) / 10.0; // 반올림 후 리턴
    }

    public void increaseLikes() {
        this.likesCount += 1;
    }

    public void decreaseLikes() {
        this.likesCount = this.likesCount > 0 ? this.likesCount - 1 : 0;
    }

}
