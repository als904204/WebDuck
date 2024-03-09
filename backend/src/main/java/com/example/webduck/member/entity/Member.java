package com.example.webduck.member.entity;

import com.example.webduck.global.common.BaseTime;
import com.example.webduck.global.converter.EncryptorConverter;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Entity
public class Member extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Length(min = 2, max = 16)
    @Column(nullable = false, length = 16)
    private String username;

    @Convert(converter = EncryptorConverter.class)
    @Column(nullable = false,unique = true)
    private String email;

    @Convert(converter = EncryptorConverter.class)
    @Column(nullable = false)
    private String socialPk;

    @Convert(converter = EncryptorConverter.class)
    @Column(nullable = false)
    private String socialId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    // 이전 로그인 시간
    private LocalDateTime previousLoginAt;

    // 현재 로그인 시간
    private LocalDateTime currentLoginAt;

    protected Member() {
    }


    @Builder
    public Member(String username, String email, Role role, SocialType socialType,String socialId, String socialPk) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.socialPk = socialPk;
        this.socialId = socialId;
        this.socialType = socialType;
    }

    public void updateProfile(String username) {
        this.username = username;
    }

    // 요청 username 이 기존 username 이랑 같은지
    public void validateUsername(String username) {
        if (this.username.equals(username)) {
            throw new CustomException(LogicExceptionCode.DUPLICATE_REQUEST);
        }
    }

    public void updatePrevLoginAt(LocalDateTime now) {
        this.previousLoginAt = this.currentLoginAt;
        this.currentLoginAt = now;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public LocalDateTime getPreviousLoginAt() {
        return previousLoginAt;
    }


}