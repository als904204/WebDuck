package com.example.webduck.member.domain;

import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.global.security.oauth.entity.userInfo.OAuth2UserInfo;
import com.example.webduck.member.infrastructure.Role;
import com.example.webduck.member.infrastructure.SocialType;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {

    private Long id;
    private String username;
    private String email;
    private String socialPk;
    private String socialId;
    private Role role;
    private SocialType socialType;
    private LocalDateTime previousLoginAt;
    private LocalDateTime currentLoginAt;

    @Builder
    public Member(Long id, String username, String email, Role role, SocialType socialType,
        String socialId, String socialPk, LocalDateTime previousLoginAt,
        LocalDateTime currentLoginAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.socialPk = socialPk;
        this.socialId = socialId;
        this.socialType = socialType;
        this.previousLoginAt = previousLoginAt;
        this.currentLoginAt = currentLoginAt;
    }

    public static Member save(OAuth2UserInfo oAuth2UserInfo, String randomNickname) {
        return Member.builder()
            .username(randomNickname)
            .email(oAuth2UserInfo.getEmail())
            .socialType(oAuth2UserInfo.getSocialType())
            .role(Role.USER)
            .socialId(oAuth2UserInfo.getId())
            .socialPk(oAuth2UserInfo.getPk())
            .build();
    }
    public Member update(MemberUpdate update) {
        if (this.username.equals(update.getUsername())) {
            throw new CustomException(LogicExceptionCode.DUPLICATE_REQUEST);
        }
        return Member.builder()
            .username(update.getUsername())
            .id(id)
            .email(email)
            .role(role)
            .socialPk(socialPk)
            .socialId(socialId)
            .socialType(getSocialType())
            .build();
    }

    public void updatePrevLoginAt(LocalDateTime now) {
        this.previousLoginAt = this.currentLoginAt;
        this.currentLoginAt = now;
    }

}
