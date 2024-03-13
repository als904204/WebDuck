package com.example.webduck.review.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.webduck.global.common.SliceResponse;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.security.oauth.entity.SessionMember;
import com.example.webduck.member.domain.Member;
import com.example.webduck.member.infrastructure.Role;
import com.example.webduck.member.infrastructure.SocialType;
import com.example.webduck.mock.member.FakeMemberRepository;
import com.example.webduck.mock.review.FakeReviewRepository;
import com.example.webduck.mock.webtoon.FakeWebtoonRepository;
import com.example.webduck.review.controller.response.ReviewSliceResponse;
import com.example.webduck.review.domain.Review;
import com.example.webduck.review.domain.ReviewCreate;
import com.example.webduck.webtoon.domain.Webtoon;
import com.example.webduck.webtoon.infrastructure.Platform;
import com.example.webduck.webtoon.infrastructure.PublishDay;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

// TODO fake repo
class ReviewServiceTest {

    private ReviewServiceImpl reviewService;

    @BeforeEach
    void init() {
        FakeMemberRepository fakeMemberRepository = new FakeMemberRepository();
        FakeReviewRepository fakeReviewRepository = new FakeReviewRepository();
        FakeWebtoonRepository fakeWebtoonRepository = new FakeWebtoonRepository();

        reviewService = ReviewServiceImpl.builder()
            .memberRepository(fakeMemberRepository)
            .reviewRepository(fakeReviewRepository)
            .webtoonRepository(fakeWebtoonRepository)
            .build();

        fakeReviewRepository.save(Review.builder()
            .id(1L)
            .memberId(1L)
            .reviewerNickname("WebDuck")
            .webtoonId(1L)
            .content("fun")
            .rating(5)
            .likesCount(0)
            .build());

        fakeWebtoonRepository.save(Webtoon.builder()
            .id(1L)
            .title("title")
            .summary("summary")
            .originalImageName("name")
            .platform(Platform.NAVER)
            .publishDay(PublishDay.MONDAY)
            .webtoonUrl("url")
            .author("author")
            .reviewCount(5)
            .author("WebDuck")
            .build());

        fakeMemberRepository.save(Member.builder()
            .id(1L)
            .socialPk("pk")
            .socialId("id")
            .socialType(SocialType.GOOGLE)
            .role(Role.USER)
            .username("WebDuck")
            .build());

        fakeMemberRepository.save(Member.builder()
            .id(2L)
            .socialPk("pk")
            .socialId("id")
            .socialType(SocialType.GOOGLE)
            .role(Role.USER)
            .username("WebDuck2")
            .build());
    }

    @DisplayName("성공 : 리뷰 생성")
    @Test
    void createReviewSuccess() {

        Member member = Member.builder()
            .id(1L)
            .username("WebDuck")
            .build();

        SessionMember sessionMember = new SessionMember(member);

        ReviewCreate reviewCreate = new ReviewCreate(1L, "fun", 4);

        Review review = reviewService.create(sessionMember, reviewCreate);

        assertThat(review.getId()).isEqualTo(1L);
        assertThat(review.getContent()).isEqualTo("fun");
        assertThat(review.getWebtoonId()).isEqualTo(1L);
        assertThat(review.getRating()).isEqualTo(4);
        assertThat(review.getReviewerNickname()).isEqualTo("WebDuck");
        assertThat(review.getLikesCount()).isEqualTo(0);
    }

    @DisplayName("실패 : 존재하지 않는 웹툰 ID 리뷰 생성")
    @Test
    void createReviewFail_not_exists_webtoon() {

        Member member = Member.builder()
            .id(1L)
            .username("WebDuck")
            .build();

        SessionMember sessionMember = new SessionMember(member);

        ReviewCreate reviewCreate = new ReviewCreate(9999L, "fun", 4);

        assertThatThrownBy(() -> {
            reviewService.create(sessionMember,reviewCreate);
        }).isInstanceOf(CustomException.class);
    }

    @DisplayName("실패 : 존재하지 않는 회원 ID 리뷰 생성")
    @Test
    void createReviewFail_not_exists_member() {

        Member member = Member.builder()
            .id(9999L)
            .username("WebDuck")
            .build();

        SessionMember sessionMember = new SessionMember(member);

        ReviewCreate reviewCreate = new ReviewCreate(1L, "fun", 4);

        assertThatThrownBy(() -> {
            reviewService.create(sessionMember,reviewCreate);
        }).isInstanceOf(CustomException.class);
    }

    @DisplayName("성공 : 리뷰 삭제")
    @Test
    void deleteReview() {
        Member member = Member.builder()
            .id(1L)
            .username("WebDuck")
            .build();

        SessionMember sessionMember = new SessionMember(member);
        reviewService.deleteReview(sessionMember, 1L);
    }

    @DisplayName("실패 : 리뷰 삭제 작성자 불일치")
    @Test
    void deleteReviewFail_miss_match_review_author() {
        Member member = Member.builder()
            .id(2L)
            .username("WebDuck")
            .build();

        SessionMember sessionMember = new SessionMember(member);

        assertThatThrownBy(() -> {
            reviewService.deleteReview(sessionMember, 1L);
        }).isInstanceOf(CustomException.class);
    }

    @DisplayName("실패 : 존재하지 않는 리뷰 삭제 ")
    @Test
    void deleteReviewFail_not_exists_review() {
        Member member = Member.builder()
            .id(1L)
            .username("WebDuck")
            .build();

        SessionMember sessionMember = new SessionMember(member);

        assertThatThrownBy(() -> {
            reviewService.deleteReview(sessionMember, 9999L);
        }).isInstanceOf(CustomException.class);
    }

    @DisplayName("성공 : 리뷰 평균 점수")
    @Test
    void reviewAvg() {
        Double avg = reviewService.getAvg(1L);
        assertThat(avg).isEqualTo(5.0);
    }

    @DisplayName("성공 : 리뷰 평균 개수")
    @Test
    void reviewCount() {
        int count = reviewService.getCount(1L);
        assertThat(count).isEqualTo(1);
    }




}