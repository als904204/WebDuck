package com.example.webduck.member.repository;

import com.example.webduck.config.ConverterConfigTest;
import com.example.webduck.config.QueryDslConfigTest;
import com.example.webduck.member.entity.Member;
import com.example.webduck.member.entity.Role;
import com.example.webduck.member.entity.SocialType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@Import({QueryDslConfigTest.class, ConverterConfigTest.class})
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    private Member member;

    private final String username = "webduck";
    private final SocialType socialType = SocialType.GOOGLE;
    private final String email = "webduck@test.com";
    private final String socialPk = "socialPk";
    private final String socialId = "socialId";

    @BeforeEach
    void setUp() {
        member = Member.builder()
            .username(username)
            .socialType(socialType)
            .socialPk(socialPk)
            .socialId(socialId)
            .role(Role.USER)
            .email(email)
            .build();
        memberRepository.save(member);
    }

    @Test
    void findByUsername() {
        Member findMember = memberRepository.findByUsername(username).orElseThrow();

        Assertions.assertThat(findMember).isNotNull();
        Assertions.assertThat(findMember.getUsername()).isEqualTo(username);
    }
}