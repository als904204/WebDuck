package com.example.webduck.member.controller.response;

import com.example.webduck.member.domain.Profile;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfileResponse {

    private final String username;
    private final LocalDateTime prevLoginAt;
    private final int reviewCount;
    private final int likesCount;

    public static ProfileResponse from(Profile profile) {
        return ProfileResponse.builder()
            .username(profile.getUsername())
            .prevLoginAt(profile.getPrevLoginAt())
            .reviewCount(profile.getReviewCount())
            .likesCount(profile.getLikesCount())
            .build();
    }

}
