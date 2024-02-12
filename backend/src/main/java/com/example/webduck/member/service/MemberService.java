package com.example.webduck.member.service;

import com.example.webduck.config.security.oauth.entity.SessionMember;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.member.dto.MemberProfile.ProfileRequest;
import com.example.webduck.member.dto.MemberProfile.ProfileResponse;
import com.example.webduck.member.entity.Member;
import com.example.webduck.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    @Transactional(readOnly = true)
    public ProfileResponse getMemberProfile(SessionMember sessionMember) {
        Long sessionMemberId = sessionMember.getId();

        Member member = memberRepository.findById(sessionMemberId)
            .orElseThrow(() -> new CustomException(
                LogicExceptionCode.MEMBER_NOT_FOUND));

        return new ProfileResponse(member);
    }


    @Transactional
    public void updateMemberProfile(SessionMember sessionMember, ProfileRequest request) {
        Long sessionMemberId = sessionMember.getId();

        Member member = memberRepository.findById(sessionMemberId)
            .orElseThrow(() -> new CustomException(
                LogicExceptionCode.MEMBER_NOT_FOUND));

        String username = request.getUsername();

        if (member.getUsername().equals(username)) {
            log.warn("request username is same as before={}",username);
            throw new CustomException(LogicExceptionCode.DUPLICATE_REQUEST);
        }

        boolean isDuplicatedName = memberRepository.existsByUsername(username);

        if (isDuplicatedName) {
            log.warn("duplicate username request={}",username);
            throw new CustomException(LogicExceptionCode.DUPLICATE_REQUEST);
        }

        member.updateProfile(username);
        sessionMember.setUsername(username);
    }
}
