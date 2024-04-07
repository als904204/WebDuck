package com.example.webduck.auth.service;

import com.example.webduck.auth.controller.port.AuthService;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.global.security.oauth.entity.SessionMember;
import com.example.webduck.member.domain.Member;
import com.example.webduck.member.infrastructure.Role;
import com.example.webduck.member.service.port.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final MemberRepository memberRepository;

    public Member me(SessionMember sessionMember) {
        return memberRepository.findByIdAneUsername(sessionMember.getId(),
                sessionMember.getUsername())
            .orElseThrow(() -> new CustomException(LogicExceptionCode.MEMBER_NOT_FOUND));
    }

}
