package com.example.webduck.member.controller;

import com.example.webduck.config.security.oauth.dto.LoginMember;
import com.example.webduck.config.security.oauth.entity.SessionMember;
import com.example.webduck.member.domain.MemberProfile;
import com.example.webduck.member.dto.MemberUpdate.ProfileRequest;
import com.example.webduck.member.dto.MemberUpdate.ProfileResponse;
import com.example.webduck.member.service.MemberService;

import javax.validation.Valid;
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
    public ResponseEntity<MemberProfile> getProfile(@LoginMember SessionMember sessionMember) {
        MemberProfile profile = memberService.getProfile(sessionMember);
        return ResponseEntity.ok(profile);
    }

    // 모든 자원 변경 아님, PUT X -> PATCH O
    @PatchMapping("/profile")
    public ResponseEntity<ProfileResponse> updateProfile(@LoginMember SessionMember sessionMember,
        @RequestBody @Valid ProfileRequest profileRequest) {
        ProfileResponse profileResponse = memberService.updateMemberProfile(sessionMember,
            profileRequest);
        return ResponseEntity.ok(profileResponse);
    }

}
