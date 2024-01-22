package com.example.webduck.member.entity;

import com.example.webduck.global.converter.EncryptorConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
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
}