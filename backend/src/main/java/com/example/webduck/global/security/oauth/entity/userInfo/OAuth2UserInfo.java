package com.example.webduck.global.security.oauth.entity.userInfo;

import com.example.webduck.member.entity.SocialType;
import java.util.Map;

public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    protected String userNameAttributeName;

    public OAuth2UserInfo(Map<String, Object> attributes,String userNameAttributeName) {
        this.attributes = attributes;
        this.userNameAttributeName = userNameAttributeName;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public abstract String getId();
    public abstract String getPk();
    public abstract String getName();
    public abstract String getEmail();
    public abstract String userNameAttributeName();
    public abstract SocialType getSocialType();
}
