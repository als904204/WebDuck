package com.example.webduck.member.domain;

import com.example.webduck.member.infrastructure.MemberEntity;
import com.example.webduck.review.entity.Review;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;


public class Profile {

    private final String username;
    private final LocalDateTime prevLoginAt;
    private final int reviewCount;
    private final int likesCount;

    @Builder(access = AccessLevel.PRIVATE)
    private Profile(String username, LocalDateTime prevLoginAt, int reviewCount,
        int likesCount) {
        assert username != null && !username.isEmpty() : "username은 null이거나 비어있을 수 없습니다.";
        assert reviewCount >= 0 && likesCount >= 0 : "리뷰 개수와 좋아요 개수는 음수가 될 수 없습니다.";
        this.username = username;
        this.prevLoginAt = prevLoginAt;
        this.reviewCount = reviewCount;
        this.likesCount = likesCount;
    }

    // 회원정보,회원 리뷰 개수,받은 좋아요 수
    public static Profile from(Member memberEntity, List<Review> memberReviews) {

        // 좋아요 개수
        int likesCount = 0;
        for (Review review : memberReviews) {
            likesCount += review.getLikesCount();
        }

        // 리뷰 개수
        int reviewCount = memberReviews.size();

        return Profile.builder()
            .username(memberEntity.getUsername())
            .prevLoginAt(memberEntity.getPreviousLoginAt())
            .reviewCount(reviewCount)
            .likesCount(likesCount)
            .build();
    }


    public String getUsername() {
        return username;
    }

    public LocalDateTime getPrevLoginAt() {
        return prevLoginAt;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public int getLikesCount() {
        return likesCount;
    }

}
