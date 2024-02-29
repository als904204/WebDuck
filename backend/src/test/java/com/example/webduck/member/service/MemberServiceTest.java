package com.example.webduck.member.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.example.webduck.config.security.oauth.entity.SessionMember;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.member.dto.MemberUpdate.ProfileRequest;
import com.example.webduck.member.entity.Member;
import com.example.webduck.member.repository.MemberRepository;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class MemberServiceTest {

    @InjectMocks
    private MemberService memberService;

    @Mock
    private MemberRepository memberRepository;

    @DisplayName("실패 : 없는 회원 프로필 조회")
    @Test
    void testGetMemberProfileFail() {
        var memberId = 1L;
        var username = "WebDuck";

        Member member = Mockito.mock(Member.class);
        Mockito.when(member.getId()).thenReturn(memberId);
        Mockito.when(member.getUsername()).thenReturn(username);
        Mockito.when(memberRepository.findById(memberId)).thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(
            () -> memberService.getProfile(new SessionMember(member)));

        Assertions.assertThat(thrown)
            .isInstanceOf(CustomException.class)
            .hasMessage("Member not found");
    }

    @DisplayName("성공 : 회원 프로필 수정 ")
    @Test
    void testUpdateMemberProfile() {

        // given
        var memberId = 1L;
        var originalUsername = "OriginalWebDuck";
        var requestUsername = "UpdateWebDuck";

        Member member = Mockito.mock(Member.class);
        Mockito.when(member.getId()).thenReturn(memberId);
        Mockito.when(member.getUsername()).thenReturn(originalUsername);
        Mockito.when(memberRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(member));
        Mockito.when(memberRepository.existsByUsername(requestUsername)).thenReturn(false);

        var sessionMember = new SessionMember(member);
        var profileRequest = new ProfileRequest(requestUsername);

        // when
        memberService.updateMemberProfile(sessionMember, profileRequest);

        // then
        Mockito.verify(member).updateProfile(requestUsername);
        Mockito.verify(memberRepository).existsByUsername(requestUsername);

        // 세션에 있는 username 이 업데이트 요청한 username 과 같은가? (동기화)
        Assertions.assertThat(sessionMember.getUsername()).isEqualTo(requestUsername);
    }

    @DisplayName("실패 : 회원 프로필 실패 (이전과 같은 닉네임 요청)")
    @Test
    void testUpdateMemberProfile_sameAsBefore_username() {

        // given
        var memberId = 1L;
        var originalUsername = "WebDuck";
        var sameUsername = "WebDuck";

        Member member = Mockito.mock(Member.class);
        Mockito.when(member.getId()).thenReturn(memberId);
        Mockito.when(member.getUsername()).thenReturn(originalUsername);
        Mockito.when(memberRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(member));

        var sessionMember = new SessionMember(member);
        var profileRequest = new ProfileRequest(sameUsername);

        // when
        Throwable thrown = catchThrowable(
            () -> memberService.updateMemberProfile(sessionMember, profileRequest));

        // expect
        assertThat(thrown).isInstanceOf(CustomException.class)
            .hasMessage("Duplicate request");

    }
}