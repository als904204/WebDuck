package com.example.webduck.member.service;

import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.global.security.oauth.entity.SessionMember;
import com.example.webduck.global.security.oauth.entity.userInfo.OAuth2UserInfo;
import com.example.webduck.member.controller.port.MemberService;
import com.example.webduck.member.domain.Member;
import com.example.webduck.member.domain.MemberUpdate;
import com.example.webduck.member.domain.Profile;
import com.example.webduck.member.service.port.MemberRepository;
import com.example.webduck.member.service.port.NicknameGenerator;
import com.example.webduck.review.domain.Review;
import com.example.webduck.review.service.port.ReviewRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Builder
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final ReviewRepository reviewRepository;

    private final NicknameGenerator nicknameGenerator;


    @Override
    public Member authenticate(OAuth2UserInfo oAuth2UserInfo) {
        // 기존 회원 최근접속 업데이트 또는 신규 회원 생성
        Member member = memberRepository.findByEmailAndSocialType(
                oAuth2UserInfo.getEmail(),
                oAuth2UserInfo.getSocialType())
            .orElseGet(() -> {

                // 신규 회원일 경우, 랜덤 닉네임으로 회원 생성
                String randomNickname;
                boolean isExistsName;
                do {
                    randomNickname = nicknameGenerator.create();
                    isExistsName = memberRepository.findByUsername(randomNickname).isPresent();
                } while (isExistsName);

                return Member.create(oAuth2UserInfo, randomNickname);
            });

        // 최근 로그인 시간 업데이트 후 저장
        member.updatePrevLoginAt(LocalDateTime.now());
        return memberRepository.save(member);
    }

    @Override
    public Profile getProfile(SessionMember sessionMember) {
        Long id = sessionMember.getId();
        Member member = memberRepository.getById(id);
        List<Review> reviews = reviewRepository.findByMemberId(id);
        return Profile.from(member, reviews);
    }


    @Override
    public Member updateMember(SessionMember sessionMember, MemberUpdate memberUpdate) {

        Long id = sessionMember.getId();

        boolean existsByUsername = memberRepository.existsByUsername(memberUpdate.getUsername());

        if (existsByUsername) {
            log.error("exists username m.id={}, req.username={} ", id, memberUpdate.getUsername());
            throw new CustomException(LogicExceptionCode.DUPLICATE_REQUEST);
        }

        Member member = memberRepository.getById(id);
        member = member.update(memberUpdate);

        member = memberRepository.save(member);
        return member;
    }



}
