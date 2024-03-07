package com.example.webduck.global.security.oauth.entity.userInfo;

import com.example.webduck.member.entity.SocialType;
import java.util.Map;

public class KakaoUserInfo extends OAuth2UserInfo {
    public KakaoUserInfo(Map<String, Object> attributes,String userNameAttributeName) {
        super(attributes, userNameAttributeName);
    }

    @Override
    public String getId() {
        return attributes.get("id").toString();
    }



    @Override
    public String getName() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        return (String) properties.get("nickname");
    }

    @Override
    public String getEmail() {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        return (String) kakaoAccount.get("email");
    }

    @Override
    public String userNameAttributeName() {
        return this.userNameAttributeName;
    }

    @Override
    public SocialType getSocialType() {
        return SocialType.KAKAO;
    }

    @Override
    public String getPk() {
        return "id";
    }

}

