package com.example.webduck.member.dto;

import com.example.webduck.member.entity.Member;
import com.example.webduck.member.entity.Role;
import lombok.Getter;

@Getter
public class MemberProfile {

    private final Long id;
    private final String username;
    private final Role role;

    public MemberProfile(Member member) {
        this.id = member.getId();
        this.username = member.getUsername();
        this.role = member.getRole();
    }
}
