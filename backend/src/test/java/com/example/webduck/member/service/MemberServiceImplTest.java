package com.example.webduck.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.security.oauth.entity.SessionMember;
import com.example.webduck.global.security.oauth.entity.userInfo.GoogleUserInfo;
import com.example.webduck.global.security.oauth.entity.userInfo.OAuth2UserInfo;
import com.example.webduck.member.domain.Member;
import com.example.webduck.member.domain.MemberUpdate;
import com.example.webduck.member.domain.Profile;
import com.example.webduck.member.infrastructure.Role;
import com.example.webduck.member.infrastructure.SocialType;
import com.example.webduck.mock.member.FakeMemberRepository;
import com.example.webduck.mock.member.FakeNicknameGenerator;
import com.example.webduck.mock.review.FakeReviewRepository;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberServiceImplTest {

    private MemberServiceImpl memberService;

    @BeforeEach
    void init() {
        FakeNicknameGenerator fakeNicknameGenerator = new FakeNicknameGenerator();
        FakeReviewRepository fakeReviewRepository = new FakeReviewRepository();
        FakeMemberRepository fakeMemberRepository = new FakeMemberRepository();

        memberService = MemberServiceImpl.builder()
            .nicknameGenerator(fakeNicknameGenerator)
            .reviewRepository(fakeReviewRepository)
            .memberRepository(fakeMemberRepository)
            .build();

        fakeMemberRepository.save(Member.builder()
            .id(1L)
            .username("WebDuck")
            .email("WebDuck@WebDuck")
            .role(Role.USER)
            .socialType(SocialType.GOOGLE)
            .socialId("s_id")
            .socialPk("sub")
            .build());

    }

    @DisplayName("성공 : 회원가입")
    @Test
    void authenticate_signup() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", "123");
        attributes.put("name", "webduck#9999");
        attributes.put("email", "WebDuck@WebDuck2");

        String userNameAttributeName = "sub";
        OAuth2UserInfo oAuth2UserInfo = new GoogleUserInfo(attributes, userNameAttributeName);

        Member authenticate = memberService.authenticate(oAuth2UserInfo);

        assertThat(authenticate.getId()).isEqualTo(1L);
        assertThat(authenticate.getUsername()).isEqualTo("webduck#9999");
        assertThat(authenticate.getSocialPk()).isEqualTo("sub");
        assertThat(authenticate.getSocialId()).isEqualTo("123");
        assertThat(authenticate.getSocialType()).isEqualTo(SocialType.GOOGLE);
        assertThat(authenticate.getRole()).isEqualTo(Role.USER);
    }

    @DisplayName("성공 : 로그인")
    @Test
    void authenticate_login() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", "123");
        attributes.put("name", "WebDuck");
        attributes.put("email", "WebDuck@WebDuck");

        String userNameAttributeName = "sub";
        OAuth2UserInfo oAuth2UserInfo = new GoogleUserInfo(attributes, userNameAttributeName);

        Member authenticate = memberService.authenticate(oAuth2UserInfo);

        assertThat(authenticate.getId()).isEqualTo(1L);
        assertThat(authenticate.getUsername()).isEqualTo("WebDuck");
        assertThat(authenticate.getSocialPk()).isEqualTo("sub");
        assertThat(authenticate.getSocialId()).isEqualTo("s_id");
        assertThat(authenticate.getSocialType()).isEqualTo(SocialType.GOOGLE);
        assertThat(authenticate.getRole()).isEqualTo(Role.USER);
    }

    @DisplayName("성공 : 프로필 조회")
    @Test
    void getProfile() {

        Member member = Member.builder()
            .id(1L)
            .username("WebDuck")
            .build();

        SessionMember sessionMember = new SessionMember(member);

        Profile profile = memberService.getProfile(sessionMember);
        assertThat(profile.getUsername()).isEqualTo("WebDuck");
        assertThat(profile.getLikesCount()).isEqualTo(0);
        assertThat(profile.getReviewCount()).isEqualTo(0);
    }

    @DisplayName("실패 : 없는 회원 프로필 조회")
    @Test
    void getProfile_fail_not_exists_member() {

        Member member = Member.builder()
            .id(999L)
            .username("WebDuck")
            .build();

        SessionMember sessionMember = new SessionMember(member);

        assertThatThrownBy(() -> {
            memberService.getProfile(sessionMember);
        }).isInstanceOf(CustomException.class);
    }


    @DisplayName("성공 : 프로필 업데이트")
    @Test
    void updateMember() {

        Member member = Member.builder()
            .id(1L)
            .username("WebDuck")
            .build();

        SessionMember sessionMember = new SessionMember(member);

        MemberUpdate update = new MemberUpdate("new WebDuck");

        Member updatedMember = memberService.updateMember(sessionMember, update);
        assertThat(updatedMember.getUsername()).isEqualTo("new WebDuck");
    }

    @DisplayName("실패 : 없는 회원 프로필 업데이트")
    @Test
    void updateProfile_fail_not_exists_member() {

        Member member = Member.builder()
            .id(999L)
            .username("WebDuck")
            .build();

        SessionMember sessionMember = new SessionMember(member);
        MemberUpdate update = new MemberUpdate("new WebDuck");

        assertThatThrownBy(() -> {
            memberService.updateMember(sessionMember, update);
        }).isInstanceOf(CustomException.class);
    }

    @DisplayName("실패 : 회원 프로필 중복 username 업데이트")
    @Test
    void updateProfile_fail_duplicated_username_member() {

        Member member = Member.builder()
            .id(1L)
            .username("WebDuck")
            .build();

        SessionMember sessionMember = new SessionMember(member);
        MemberUpdate update = new MemberUpdate("WebDuck");

        assertThatThrownBy(() -> {
            memberService.updateMember(sessionMember, update);
        }).isInstanceOf(CustomException.class);
    }

}