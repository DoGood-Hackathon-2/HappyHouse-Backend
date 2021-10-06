package com.example.happyhousebackend.config.auth.dto;

public enum OAuthType {

    GOOGLE("google"),

    KAKAO("kakao"),

    NAVER("naver");

    private final String type;

    OAuthType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
