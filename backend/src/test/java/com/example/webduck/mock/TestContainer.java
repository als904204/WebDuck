package com.example.webduck.mock;

import com.example.webduck.member.controller.MemberApiController;
import com.example.webduck.member.controller.port.MemberService;
import com.example.webduck.member.service.MemberServiceImpl;
import com.example.webduck.member.service.port.MemberRepository;
import com.example.webduck.member.service.port.NicknameGenerator;
import com.example.webduck.mock.member.FakeMemberRepository;
import com.example.webduck.mock.review.FakeReviewRepository;
import com.example.webduck.review.service.port.ReviewRepository;
import lombok.Builder;

public class TestContainer {

    public final MemberRepository memberRepository;
    public final MemberService memberService;
    public final MemberApiController memberApiController;
    public final ReviewRepository reviewRepository;

    @Builder
    public TestContainer(NicknameGenerator nicknameGenerator) {
        this.memberRepository = new FakeMemberRepository();
        this.reviewRepository = new FakeReviewRepository();

        this.memberService = MemberServiceImpl.builder()
            .memberRepository(this.memberRepository)
            .reviewRepository(this.reviewRepository)
            .nicknameGenerator(nicknameGenerator)
            .build();

        this.memberApiController = MemberApiController.builder()
            .memberService(this.memberService)
            .build();
    }


}
