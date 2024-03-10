package com.example.webduck.global.security.oauth.service;

import static com.example.webduck.global.exception.exceptionCode.ValidationExceptionCode.INVALID_OAUTH_TYPE;

import com.example.webduck.global.security.oauth.entity.SessionMember;
import com.example.webduck.global.exception.CustomException;
import com.example.webduck.global.security.oauth.entity.userInfo.GoogleUserInfo;
import com.example.webduck.global.security.oauth.entity.userInfo.KakaoUserInfo;
import com.example.webduck.global.security.oauth.entity.userInfo.OAuth2UserInfo;
import com.example.webduck.member.entity.Member;
import com.example.webduck.member.service.MemberService;
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

    private final MemberService memberService;
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

        Member member = memberService.createOrUpdateMember(oAuth2UserInfo);

        httpSession.setAttribute("member", new SessionMember(member));
        return new DefaultOAuth2User(
            Collections.singleton(new
                SimpleGrantedAuthority((member.getRole().getKey()))),
            oAuth2UserInfo.getAttributes(),
            oAuth2UserInfo.userNameAttributeName(
            ));

    }
}
