package com.example.webduck.member.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.security.oauth.entity.SessionMember;
import com.example.webduck.member.controller.response.ProfileResponse;
import com.example.webduck.member.controller.response.ProfileUpdateResponse;
import com.example.webduck.member.domain.Member;
import com.example.webduck.member.domain.MemberUpdate;
import com.example.webduck.member.infrastructure.Role;
import com.example.webduck.member.infrastructure.SocialType;
import com.example.webduck.mock.TestContainer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class MemberApiControllerUnitTest {


    @DisplayName("성공 : 회원 프로필 조회")
    @Test
    void getProfileSuccess() {
        TestContainer testContainer = TestContainer.builder()
            .build();

        Member member = Member.builder()
            .id(1L)
            .username("WebDuck")
            .email("Webduck@test.com")
            .role(Role.USER)
            .socialType(SocialType.GOOGLE)
            .socialId("s_id")
            .socialPk("s_pk")
            .build();

        testContainer.memberRepository.save(member);

        SessionMember sessionMember = new SessionMember(member);

        ResponseEntity<ProfileResponse> result = testContainer.memberApiController.getProfile(
            sessionMember);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getUsername()).isEqualTo("WebDuck");
        assertThat(result.getBody().getLikesCount()).isEqualTo(0);
        assertThat(result.getBody().getReviewCount()).isEqualTo(0);
        assertThat(result.getBody().getPrevLoginAt()).isNull();
    }



    @DisplayName("실패 : 존재하지않는 회원 프로필 조회")
    @Test
    void getProfileFail_not_exists_member() {
        TestContainer testContainer = TestContainer.builder()
            .build();

        Member member = Member.builder()
            .id(9999L)
            .username("WebDuck")
            .email("Webduck@test.com")
            .role(Role.USER)
            .socialType(SocialType.GOOGLE)
            .socialId("s_id")
            .socialPk("s_pk")
            .build();



        SessionMember sessionMember = new SessionMember(member);


            assertThatThrownBy(() -> {
                testContainer.memberApiController.getProfile(
                    sessionMember);
            }).isInstanceOf(CustomException.class);
    }

    @DisplayName("성공 : 회원 프로필 업데이트")
    @Test
    void updateProfileSuccess() {
        TestContainer testContainer = TestContainer.builder()
            .build();

        Member member = Member.builder()
            .id(1L)
            .username("WebDuck")
            .email("Webduck@test.com")
            .role(Role.USER)
            .socialType(SocialType.GOOGLE)
            .socialId("s_id")
            .socialPk("s_pk")
            .build();

        testContainer.memberRepository.save(member);

        SessionMember sessionMember = new SessionMember(member);
        MemberUpdate profileRequest = new MemberUpdate("new WebDuck");

        ResponseEntity<ProfileUpdateResponse> result = testContainer.memberApiController.updateProfile(
            sessionMember, profileRequest);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.valueOf(200));
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getBody().getUsername()).isEqualTo("new WebDuck");
    }


    @DisplayName("실패 : 존재하지않는 회원 프로필 업데이트")
    @Test
    void updateProfileFail_not_exists_member() {
        TestContainer testContainer = TestContainer.builder()
            .build();

        Member member = Member.builder()
            .id(9999L)
            .username("WebDuck")
            .email("Webduck@test.com")
            .role(Role.USER)
            .socialType(SocialType.GOOGLE)
            .socialId("s_id")
            .socialPk("s_pk")
            .build();



        SessionMember sessionMember = new SessionMember(member);
        MemberUpdate profileRequest = new MemberUpdate("new WebDuck");


        assertThatThrownBy(() -> {
            testContainer.memberApiController.updateProfile(
                sessionMember, profileRequest);
        }).isInstanceOf(CustomException.class);
    }

    @DisplayName("실패 : 중복닉네임 회원 프로필 업데이트")
    @Test
    void updateProfileFail_duplicate_member() {
        TestContainer testContainer = TestContainer.builder()
            .build();

        Member member = Member.builder()
            .id(9999L)
            .username("duplicate webDuck")
            .email("Webduck@test.com")
            .role(Role.USER)
            .socialType(SocialType.GOOGLE)
            .socialId("s_id")
            .socialPk("s_pk")
            .build();


        testContainer.memberRepository.save(member);


        SessionMember sessionMember = new SessionMember(member);
        MemberUpdate profileRequest = new MemberUpdate("duplicate webDuck");


        assertThatThrownBy(() -> {
            testContainer.memberApiController.updateProfile(
                sessionMember, profileRequest);
        }).isInstanceOf(CustomException.class);
    }

}