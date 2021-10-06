package com.example.happyhousebackend.domain.member.entity;

public enum Role {

    GUEST("ROLE_GUEST"),

    USER("ROLE_USER"),

    ADMIN("ROLE_ADMIN");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
