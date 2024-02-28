package com.example.webduck.member.entity;

import com.example.webduck.global.common.BaseTime;
import com.example.webduck.global.converter.EncryptorConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
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