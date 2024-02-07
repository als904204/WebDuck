package com.example.webduck.member.service;

import com.example.webduck.config.security.oauth.dto.LoginMember;
import com.example.webduck.config.security.oauth.entity.SessionMember;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.member.dto.MemberProfile;
import com.example.webduck.member.entity.Member;
import com.example.webduck.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    @Transactional(readOnly = true)
    public MemberProfile getMemberProfile(SessionMember sessionMember) {
        Long sessionMemberId = sessionMember.getId();
        Member member = memberRepository.findById(sessionMemberId)
            .orElseThrow(() -> new CustomException(
                LogicExceptionCode.MEMBER_NOT_FOUND));

        return new MemberProfile(member);
    }



}
