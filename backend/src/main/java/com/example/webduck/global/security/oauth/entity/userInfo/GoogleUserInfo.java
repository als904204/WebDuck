package com.example.webduck.global.security.oauth.entity.userInfo;

import com.example.webduck.member.entity.SocialType;
import java.util.Map;

public class GoogleUserInfo extends OAuth2UserInfo {

    public GoogleUserInfo(Map<String, Object> attributes,String userNameAttributeName) {
        super(attributes, userNameAttributeName);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String userNameAttributeName() {
        return this.userNameAttributeName;
    }

    @Override
    public SocialType getSocialType() {
        return SocialType.GOOGLE;
    }


    @Override
    public String getPk() {
        return "sub";
    }


}

