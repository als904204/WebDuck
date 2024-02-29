package com.example.webduck.member.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.webduck.member.entity.Member;
import com.example.webduck.member.entity.Role;
import com.example.webduck.member.entity.SocialType;
import com.example.webduck.review.entity.Review;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberProfileTest {

    @DisplayName("성공 : 회원 정보 조회")
    @Test
    void 회원_정보_조회_성공() {
        var nickname = "webduck";
        Member member = Member.builder()
            .role(Role.USER)
            .socialId("s.id")
            .email("test@email.com")
            .socialPk("s.pk")
            .username(nickname)
            .socialType(SocialType.GOOGLE)
            .build();

        List<Review> reviews = List.of(Review.builder()
                .webtoonId(1L)
                .memberId(1L)
                .reviewerNickname(nickname)
                .content("review content1")
                .rating(5)
                .build(),

            Review.builder()
                .webtoonId(1L)
                .memberId(1L)
                .reviewerNickname(nickname)
                .content("review content2")
                .rating(5)
                .build()
        );

        MemberProfile profile = MemberProfile.of(member, reviews);
        assertThat(profile.getUsername()).isEqualTo(nickname);
        assertThat(profile.getReviewCount()).isEqualTo(2);


    }

    @DisplayName("실패 : 회원 정보 조회 이름 null")
    @Test
    void 회원_정보_이름이_null() {
        var nickname = "webduck";
        Member member = Member.builder()
            .role(Role.USER)
            .socialId("s.id")
            .email("test@email.com")
            .socialPk("s.pk")
            .username(null)
            .socialType(SocialType.GOOGLE)
            .build();

        List<Review> reviews = List.of(Review.builder()
                .webtoonId(1L)
                .memberId(1L)
                .reviewerNickname(nickname)
                .content("review content1")
                .rating(5)
                .build(),

            Review.builder()
                .webtoonId(1L)
                .memberId(1L)
                .reviewerNickname(nickname)
                .content("review content2")
                .rating(5)
                .build()
        );

        assertThatThrownBy(() -> MemberProfile.of(member, reviews))
            .isInstanceOf(AssertionError.class)
            .hasMessageContaining("username은 null이거나 비어있을 수 없습니다.");

    }


}