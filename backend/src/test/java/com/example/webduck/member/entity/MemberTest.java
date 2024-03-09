package com.example.webduck.member.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import com.example.webduck.global.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {


    @DisplayName("실패 : 기존 username 중복요청")
    @Test
    void sameAsBefore_username_fail() {
        String duplicateUsername = "Webduck";
        Member member = Member.builder()
            .username("Webduck")
            .email("email")
            .role(Role.USER)
            .socialType(SocialType.GOOGLE)
            .socialId("id")
            .socialPk("p")
            .build();

        Throwable thrown = catchThrowable(() -> member.validateUsername(duplicateUsername));

        assertThat(thrown).isInstanceOf(CustomException.class)
            .hasMessage("Duplicate request");
    }

}