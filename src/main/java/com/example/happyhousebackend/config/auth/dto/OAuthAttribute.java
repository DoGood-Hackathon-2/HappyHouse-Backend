package com.example.happyhousebackend.config.auth.dto;

import com.example.happyhousebackend.domain.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString(exclude = "attributes")
@Builder
public class OAuthAttribute {

    private final Map<String, Object> attributes;
    private final String attributeKey;
    private final String email;
    private final String name;
    private final String picture;
    private final String socialType;
    private final String socialId;

    // TODO: 구글 외 OAuth2
    public static OAuthAttribute of(String socialType,
                                    String attributeKey,
                                    Map<String, Object> attributes) {
        if (OAuthType.GOOGLE.getType().equals(socialType)) {
            return ofGoogle(attributeKey, attributes);
        }
        throw new RuntimeException();
    }

    private static OAuthAttribute ofGoogle(String attributeKey, Map<String, Object> attributes) {
        return OAuthAttribute.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .socialType(OAuthType.GOOGLE.getType())
                .socialId((String) attributes.get("sub"))
                .attributes(attributes)
                .attributeKey(attributeKey)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .email(email)
                .nickname(name)
                .image(picture)
                .socialType(socialType)
                .socialId(socialId)
                .build();
    }
}
