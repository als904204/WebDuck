package com.example.webduck.member.controller.port;

import com.example.webduck.global.security.oauth.entity.SessionMember;
import com.example.webduck.global.security.oauth.entity.userInfo.OAuth2UserInfo;
import com.example.webduck.member.domain.Member;
import com.example.webduck.member.domain.Profile;
import com.example.webduck.member.domain.MemberUpdate;

public interface MemberService {

    Member authenticate(OAuth2UserInfo oAuth2UserInfo);

    Profile getProfile(SessionMember sessionMember);

    Member updateMember(SessionMember sessionMember, MemberUpdate update);



}