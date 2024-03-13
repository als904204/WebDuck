package com.example.webduck.member.controller.response;

import com.example.webduck.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ProfileUpdateResponse {

    private String username;

    public static ProfileUpdateResponse from(Member member) {
        return ProfileUpdateResponse.builder()
            .username(member.getUsername())
            .build();
    }

}
