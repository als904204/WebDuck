package com.example.webduck.member.infrastructure;

import com.example.webduck.config.ConverterConfigTest;
import com.example.webduck.config.QueryDslConfigTest;
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
class MemberEntityJpaRepositoryTest {

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    private MemberEntity memberEntity;

    private final String username = "webduck";
    private final SocialType socialType = SocialType.GOOGLE;
    private final String email = "webduck@test.com";
    private final String socialPk = "socialPk";
    private final String socialId = "socialId";

    @BeforeEach
    void setUp() {
        memberEntity = MemberEntity.builder()
            .username(username)
            .socialType(socialType)
            .socialPk(socialPk)
            .socialId(socialId)
            .role(Role.USER)
            .email(email)
            .build();
        memberJpaRepository.save(memberEntity);
    }

    @Test
    void findByUsername() {
        MemberEntity findMemberEntity = memberJpaRepository.findByUsername(username).orElseThrow();

        Assertions.assertThat(findMemberEntity).isNotNull();
        Assertions.assertThat(findMemberEntity.getUsername()).isEqualTo(username);
    }
}