package com.example.webduck.member.service;


import static org.assertj.core.api.Assertions.catchThrowable;

import com.example.webduck.global.security.oauth.entity.SessionMember;
import com.example.webduck.global.exception.CustomException;
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
}