package com.example.webduck.member.controller;

import com.example.webduck.config.security.oauth.dto.LoginMember;
import com.example.webduck.config.security.oauth.entity.SessionMember;
import com.example.webduck.member.dto.MemberProfile;
import com.example.webduck.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
@RestController
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/profile")
    public ResponseEntity<MemberProfile> getProfile(@LoginMember SessionMember sessionMember) {
        MemberProfile memberProfile = memberService.getMemberProfile(sessionMember);
        return ResponseEntity.ok(memberProfile);
    }
}
