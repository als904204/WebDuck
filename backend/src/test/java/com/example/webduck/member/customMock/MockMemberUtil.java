package com.example.webduck.member.customMock;

import com.example.webduck.global.security.oauth.entity.SessionMember;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class MockMemberUtil {

    public static SessionMember getMockSessionMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (SessionMember) authentication.getPrincipal();
    }

}
