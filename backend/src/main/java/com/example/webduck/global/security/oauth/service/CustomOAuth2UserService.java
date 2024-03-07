package com.example.webduck.global.security.oauth.service;

import static com.example.webduck.global.exception.exceptionCode.ValidationExceptionCode.INVALID_OAUTH_TYPE;

import com.example.webduck.global.security.oauth.entity.SessionMember;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.security.oauth.entity.userInfo.GoogleUserInfo;
import com.example.webduck.global.security.oauth.entity.userInfo.KakaoUserInfo;
import com.example.webduck.global.security.oauth.entity.userInfo.OAuth2UserInfo;
import com.example.webduck.member.entity.Member;
import com.example.webduck.member.entity.Role;
import com.example.webduck.member.repository.MemberRepository;
import com.example.webduck.member.service.NicknameGenerator;
import java.time.LocalDateTime;
import java.util.Collections;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * OAuth2UserService : 주로 특정 비즈니스 로직이 필요하거나 OAuth2 제공자에 따른 사용자 정보 처리가 다를 때 사용자 정의 서비스를 작성(KAKAO,NAVER,GOOGLE)<br/><br/>
 * DefaultOAuth2UserService : Spring Security에서 제공하는 기본 구현체로, 표준 OAuth2 제공자에서 사용자 정보를 가져오고 OAuth2User 객체로 변환하는 일반적인 로직(ONLY ONE)
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final NicknameGenerator nicknameGenerator;
    private final HttpSession httpSession;
    private static final String KAKAO = "kakao";
    private static final String GOOGLE = "google";


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

        // OAuth2 회사이름 GOOGLE,KAKAO,NAVER
        String oAuthProviderName = userRequest.getClientRegistration().getRegistrationId();

        // OAuth2 로그인 시 키(PK)가 되는 값 (sub)
            String userNameAttributeName = userRequest
                .getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();


        OAuth2UserInfo oAuth2UserInfo;
        switch (oAuthProviderName) {
            case GOOGLE:
                oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes(), userNameAttributeName);
                break;
            case KAKAO:
                oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes(), userNameAttributeName);
                break;
            default:
                throw new CustomException(INVALID_OAUTH_TYPE);
        }


        Member member = createOrUpdateMember(oAuth2UserInfo);

        httpSession.setAttribute("member", new SessionMember(member));
        return new DefaultOAuth2User(
            Collections.singleton(new
                SimpleGrantedAuthority((member.getRoleKey()))),
            oAuth2UserInfo.getAttributes(),
            oAuth2UserInfo.userNameAttributeName(
            ));

    }

    // 로그인 시 로그인 시간을 기록한다.
    // 다음 접속 시 현재 로그인한 시간이 아닌 이전에 접속했던 로그인 시간을 보여준다
    private Member createOrUpdateMember(OAuth2UserInfo oAuth2UserInfo) {

        LocalDateTime now = LocalDateTime.now();
        return memberRepository.findByEmailAndSocialType(oAuth2UserInfo.getEmail(), oAuth2UserInfo.getSocialType())
            .map(existingMember -> {
                existingMember.updatePrevLoginAt(now); // 기존 회원이면 최근 로그인 시간 업데이트
                return memberRepository.save(existingMember);
            })
            .orElseGet(() -> createMember(oAuth2UserInfo));
    }


    private Member createMember(OAuth2UserInfo oAuth2UserInfo){
        log.info("new user={}",oAuth2UserInfo.getEmail());
        String randomNickname = nicknameGenerator.getRandomNickname();

        Member newMember = Member.builder()
            .username(randomNickname)
            .email(oAuth2UserInfo.getEmail())
            .socialType(oAuth2UserInfo.getSocialType())
            .role(Role.USER)
            .socialId(oAuth2UserInfo.getId())
            .socialPk(oAuth2UserInfo.getPk())
            .build();

        newMember.updatePrevLoginAt(LocalDateTime.now());

        return memberRepository.save(newMember);

    }

}
