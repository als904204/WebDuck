package com.example.webduck.Member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String socialPk;
    private String socialId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;


    @Builder
    public Member(String username, String email, Role role, SocialType socialType,String socialId, String socialPk) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.socialPk = socialPk;
        this.socialId = socialId;
        this.socialType = socialType;
    }

    public Member update(String username) {
        this.username = username;
        return this;
    }

    public String getRoleKey() {
        return  this.role.getKey();
    }


}