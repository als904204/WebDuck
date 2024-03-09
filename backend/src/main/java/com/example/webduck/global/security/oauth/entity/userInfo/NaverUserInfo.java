package com.example.webduck.global.security.oauth.entity.userInfo;

import com.example.webduck.member.entity.SocialType;
import java.util.Map;

public class NaverUserInfo extends OAuth2UserInfo {

    public NaverUserInfo(Map<String, Object> attributes, String userNameAttributeName) {
        super(attributes, userNameAttributeName);
    }

    @Override
    public String getId() {
        return (String) ((Map<String, Object>) attributes.get("response")).get("id");
    }

    @Override
    public String getName() {
        return (String) ((Map<String, Object>) attributes.get("response")).get("name");
    }

    @Override
    public String getEmail() {
        return (String) ((Map<String, Object>) attributes.get("response")).get("email");
    }

    @Override
    public String userNameAttributeName() {
        return this.userNameAttributeName;
    }

    @Override
    public SocialType getSocialType() {
        return SocialType.NAVER;
    }

    @Override
    public String getPk() {
        return "id";
    }
}
