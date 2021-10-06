package com.example.happyhousebackend.config.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@NoArgsConstructor
@Getter
public class UserDto {

    private String email;
    private String name;
    private String picture;

    @Builder
    public UserDto(String email, String name, String picture) {
        this.email = email;
        this.name = name;
        this.picture = picture;
    }

    public static UserDto toDto(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        return UserDto.builder()
                .email((String) attributes.get("email"))
                .name((String) attributes.get("name"))
                .picture((String) attributes.get("picture"))
                .build();
    }
}
