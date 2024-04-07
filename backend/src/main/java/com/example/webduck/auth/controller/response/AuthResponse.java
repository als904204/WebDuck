package com.example.webduck.auth.controller.response;

import com.example.webduck.member.domain.Member;
import com.example.webduck.member.infrastructure.Role;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {

    private final String username;
    private final String role;
    private final LocalDateTime prevLoginAt;

    public static AuthResponse from(Member member) {
        return AuthResponse.builder()
            .username(member.getUsername())
            .prevLoginAt(member.getPreviousLoginAt())
            .role(member.getRole().getKey())
            .build();
    }
}
