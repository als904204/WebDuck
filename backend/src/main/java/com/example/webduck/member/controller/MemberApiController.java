//package com.example.webduck.member.controller;
//
//import com.example.webduck.global.security.oauth.dto.LoginMember;
//import com.example.webduck.global.security.oauth.entity.SessionMember;
//import com.example.webduck.member.controller.port.MemberService;
//import com.example.webduck.member.controller.response.ProfileResponse;
//import com.example.webduck.member.controller.response.ProfileUpdateResponse;
//
//import com.example.webduck.member.domain.MemberUpdate;
//import javax.validation.Valid;
//import lombok.Builder;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PatchMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@Builder
//@Validated
//@RequiredArgsConstructor
//@RequestMapping("/api/v1/member")
//@RestController
//public class MemberApiController {
//
//    private final MemberService memberService;
//
//    @GetMapping("/profile")
//    public ResponseEntity<ProfileResponse> getProfile(@LoginMember SessionMember sessionMember) {
//        ProfileResponse profileResponse = ProfileResponse.from(
//            memberService.getProfile(sessionMember));
//
//        return ResponseEntity.ok().body(profileResponse);
//    }
//
//    // 모든 자원 변경 아님, PUT X -> PATCH O
//    @PatchMapping("/profile")
//    public ResponseEntity<ProfileUpdateResponse> updateProfile(
//        @LoginMember SessionMember sessionMember,
//        @RequestBody @Valid MemberUpdate memberUpdate) {
//        ProfileUpdateResponse response = ProfileUpdateResponse.from(
//            memberService.updateMember(sessionMember, memberUpdate));
//
//        return ResponseEntity.ok().body(response);
//    }
//
//}
