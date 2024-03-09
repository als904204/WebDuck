package com.example.webduck.member.service;

import com.example.webduck.global.security.oauth.entity.SessionMember;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.exception.exceptionCode.LogicExceptionCode;
import com.example.webduck.member.domain.MemberProfile;
import com.example.webduck.member.dto.MemberUpdate.ProfileRequest;
import com.example.webduck.member.dto.MemberUpdate.ProfileResponse;
import com.example.webduck.member.entity.Member;
import com.example.webduck.member.repository.MemberRepository;
import com.example.webduck.review.entity.Review;
import com.example.webduck.review.repository.ReviewRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final ReviewRepository reviewRepository;


    /**
     * 회원 프로필 정보
     *
     * @param sessionMember 세션 회원
     * @return 회원 닉네임,마지막 접속 날짜,좋아요 개수,리뷰 개수
     */
    @Transactional(readOnly = true)
    public MemberProfile getProfile(SessionMember sessionMember) {
        Long sessionMemberId = sessionMember.getId();

        // TODO 세션 회원 들어오면 필터로 검증 서비스에선 Member로
        Member member = memberRepository.findById(sessionMemberId)
            .orElseThrow(() -> new CustomException(
                LogicExceptionCode.MEMBER_NOT_FOUND));

        List<Review> memberReviews = reviewRepository.findByMemberId(sessionMemberId);

        return MemberProfile.from(member, memberReviews);
    }


    @Transactional
    public ProfileResponse updateMemberProfile(SessionMember sessionMember, ProfileRequest request) {
        Long sessionMemberId = sessionMember.getId();

        Member member = memberRepository.findById(sessionMemberId)
            .orElseThrow(() -> new CustomException(
                LogicExceptionCode.MEMBER_NOT_FOUND));

        String username = request.getUsername();

        // 요청 username 이 기존 username 이랑 같은지
        member.validateUsername(username);

        // 요청 username 이 이미 DB 에 이미 있는지
        boolean isDuplicatedName = memberRepository.existsByUsername(username);

        if (isDuplicatedName) {
            log.warn("duplicate username request={}", username);
            throw new CustomException(LogicExceptionCode.DUPLICATE_REQUEST);
        }

        member.updateProfile(username);
        sessionMember.setUsername(username);

        return new ProfileResponse(member);
    }

}
