package com.example.webduck.auth.controller.port;

import com.example.webduck.global.security.oauth.entity.SessionMember;
import com.example.webduck.member.domain.Member;

public interface AuthService {

    Member me(SessionMember sessionMember);

}
