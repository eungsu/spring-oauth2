package com.example.security.oauth2;

import java.util.Map;

public class OAuth2UserInfo {
    private final Map<String, Object> attributes;
    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getUsername(String provider) {
        if ("google".equals(provider)) {
            return (String) attributes.get("sub");
        } else if ("facebook".equals(provider)) {
            return (String) attributes.get("id");
        } else if ("github".equals(provider)) {
            return (String) attributes.get("id");
        } else if ("kakao".equals(provider)) {
            return String.valueOf(attributes.get("id"));
        }
        throw new IllegalArgumentException("지원하지 않는 서비스 제공자입니다.: " + provider);
    }

    @SuppressWarnings("unchecked")
    public String getNickname(String provider) {
        if ("google".equals(provider)) {
            return (String) attributes.get("name");
        } else if ("facebook".equals(provider)) {
            return (String) attributes.get("name");
        } else if ("github".equals(provider)) {
            return (String) attributes.get("login");
        } else if ("kakao".equals(provider)) {
            Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
            if (properties == null) {
                return null;
            }
            return (String) properties.get("nickname");
        }
        throw new IllegalArgumentException("지원하지 않는 서비스 제공자입니다.: " + provider);
    }

    @SuppressWarnings("unchecked")
    public String getEmail(String provider) {
        if ("google".equals(provider)) {
            return (String) attributes.get("email");
        } else if ("facebook".equals(provider)) {
            return (String) attributes.get("email");
        } else if ("github".equals(provider)) {
            return (String) attributes.get("email");
        } else if ("kakao".equals(provider)) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            if (kakaoAccount == null) {
                return null;
            }
            return (String) kakaoAccount.get("email");
        }
        throw new IllegalArgumentException("지원하지 않는 서비스 제공자입니다.: " + provider);
    }
}
