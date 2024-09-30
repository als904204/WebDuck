package com.example.webduck.dev.service;

import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.global.security.oauth.entity.SessionMember;
import com.example.webduck.global.security.oauth.entity.userInfo.GoogleUserInfo;
import com.example.webduck.member.domain.Member;
import com.example.webduck.member.infrastructure.Role;
import com.example.webduck.member.infrastructure.SocialType;
import com.example.webduck.member.service.port.MemberRepository;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
@Profile({"dev","docker","test"})
public class DevService {

    private final MemberRepository memberRepository;

    private final HttpSession httpSession;
    private final String email = "admin@admin.com";
    private final SocialType socialType = SocialType.GOOGLE;
    private final Environment env;

    @PostConstruct
    public void initAdmin() {
        String[] activeProfiles = env.getActiveProfiles();
        String currentProfile = String.join(", ", activeProfiles);

        log.info("current active profiles={}",currentProfile);

        
        if (memberRepository.findByEmailAndSocialType(email, socialType).isEmpty()) {

            log.info("init admin");
            Member admin = Member.builder()
                .email("admin@admin.com")
                .socialId("socialId")
                .socialPk("socialPk")
                .username("admin")
                .socialType(SocialType.GOOGLE)
                .role(Role.ADMIN)
                .build();

            memberRepository.save(admin);
        } else {
            log.info("admin is already exists");
        }
    }


    public void login() {
        Member admin = memberRepository.findByEmailAndSocialType(email, socialType).orElseThrow(() ->
            new CustomException(LogicExceptionCode.MEMBER_NOT_FOUND));

        httpSession.setAttribute("member", new SessionMember(admin));

        Map<String, Object> fakeAttributes = new HashMap<>();
        fakeAttributes.put("sub", admin.getSocialId());
        fakeAttributes.put("name", admin.getUsername());
        fakeAttributes.put("email", admin.getEmail());
        fakeAttributes.put("picture", "hello");

        List<GrantedAuthority> authorities = Collections.singletonList(
            new SimpleGrantedAuthority(admin.getRole().getKey()));

        var oAuth2Info = new GoogleUserInfo(fakeAttributes, "sub");

        var oAuth2User = new DefaultOAuth2User(
            authorities,
            oAuth2Info.getAttributes(),
            oAuth2Info.userNameAttributeName()
        );

        var adminAuth = new UsernamePasswordAuthenticationToken(
            oAuth2User, null, oAuth2User.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(adminAuth);

        String adminInfo = SecurityContextHolder.getContext().getAuthentication().toString();

        if (!adminInfo.isEmpty()) {
            log.info("successfully logged-in by admin!");
            log.info("ADMIN INFO={}", adminInfo);
        } else {
            log.error("Admin Login Failed!");
        }
    }

}
