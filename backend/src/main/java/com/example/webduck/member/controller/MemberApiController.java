package com.example.webduck.member.controller;

import com.example.webduck.config.security.oauth.dto.LoginMember;
import com.example.webduck.config.security.oauth.entity.SessionMember;
import com.example.webduck.member.dto.MemberProfile.ProfileRequest;
import com.example.webduck.member.dto.MemberProfile.ProfileResponse;
import com.example.webduck.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
@RestController
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> getProfile(@LoginMember SessionMember sessionMember) {
        ProfileResponse memberProfile = memberService.getMemberProfile(sessionMember);
        return ResponseEntity.ok(memberProfile);
    }

    // 모든 자원 변경 아님, PUT X -> PATCH O
    @PatchMapping("/profile")
    public ResponseEntity<Void> updateProfile(@LoginMember SessionMember sessionMember,
        @RequestBody @Valid ProfileRequest profileRequest) {

        memberService.updateMemberProfile(sessionMember, profileRequest);
        return ResponseEntity.noContent().build();
    }

}
