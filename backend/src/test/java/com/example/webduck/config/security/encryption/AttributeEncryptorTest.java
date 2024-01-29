package com.example.webduck.config.security.encryption;


import static org.assertj.core.api.Assertions.assertThat;

import com.example.webduck.member.entity.Member;
import com.example.webduck.member.entity.Role;
import com.example.webduck.member.entity.SocialType;
import com.example.webduck.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class AttributeEncryptorTest {

    @Autowired
    private MemberRepository memberRepository;
    private final String socialId = "socialId";
    private final String socialPk = "socialPk";
    private final String email = "email";
    private final String username = "username";
    private SocialType socialType = SocialType.KAKAO;

    @DisplayName("회원 저장 암호화 & 조회 복호화")
    @Test
    void encryptorMemberColumns() {
        Member member = Member.builder()
            .socialId(socialId)
            .socialPk(socialPk)
            .email(email)
            .username(username)
            .role(Role.USER)
            .socialType(socialType)
            .build();

        memberRepository.save(member);

        Member findMember = memberRepository.findById(member.getId()).orElseThrow();
        assertThat(findMember.getEmail()).isEqualTo(email);
        assertThat(findMember.getUsername()).isEqualTo(username);
    }
}