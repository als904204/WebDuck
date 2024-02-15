package com.example.webduck.member.customMock;

import com.example.webduck.config.security.oauth.entity.SessionMember;
import com.example.webduck.member.entity.Member;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

/**
 * SessionMember 자료형으로 Test 용 Member 를 Security Context Holder 에 등록한다.
 */
public class WithMockCustomUserSecurityContextFactory implements WithSecurityContextFactory<WithMockCustomUser> {


    @Override
    public SecurityContext createSecurityContext(WithMockCustomUser annotation) {
        final SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

        Member member = Member.builder()
            .username(annotation.username())
            .role(annotation.role())
            .socialPk(annotation.socialPk())
            .socialId(annotation.socialId())
            .email(annotation.email())
            .socialType(annotation.SOCIAL_TYPE())
            .build();

        SessionMember sessionMember = new SessionMember(member);

        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            sessionMember, null, List.of(new SimpleGrantedAuthority(annotation.role().getKey())));

        securityContext.setAuthentication(authenticationToken);
        return securityContext;
    }
}
