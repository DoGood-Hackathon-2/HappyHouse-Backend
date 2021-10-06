package com.example.happyhousebackend.config.auth.service;

import com.example.happyhousebackend.config.auth.dto.OAuthAttribute;
import com.example.happyhousebackend.domain.member.entity.Member;
import com.example.happyhousebackend.domain.member.entity.Role;
import com.example.happyhousebackend.domain.member.repository.MemberRepository;
import io.jsonwebtoken.lang.Assert;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
public class CustomOAuthUserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    public CustomOAuthUserService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        Assert.notNull(userRequest, "userRequest must not null");

        final OAuth2UserService<OAuth2UserRequest, OAuth2User> userService = new DefaultOAuth2UserService();
        final OAuth2User oAuth2User = userService.loadUser(userRequest);
        final String socialType = userRequest.getClientRegistration().getRegistrationId();
        final String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        final OAuthAttribute attribute = OAuthAttribute.of(socialType, userNameAttributeName, oAuth2User.getAttributes());

        log.info("{}", attribute);
        //oAuth2User.getAttributes().forEach((k, v) -> log.info(k + ": " + v));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(Role.USER.getRole())),
                attribute.getAttributes(),
                attribute.getAttributeKey()
        );
    }

    private void saveOrUpdateMember(OAuthAttribute attribute) {
        saveOrUpdateMember(attribute, null);
    }

    public void saveOrUpdateMember(OAuthAttribute attribute, String refreshToken) {
        final Member member = memberRepository.findByEmail(attribute.getEmail())
                .map(entity -> entity.update(attribute.getAttributeKey(), attribute.getPicture()))
                .orElse(attribute.toEntity());
        member.updateRefreshToken(refreshToken);

        memberRepository.save(member);
    }

    public void updateRefreshToken(String email, String refreshToken) {
        final Member member = memberRepository.findByEmail(email).orElseThrow();
        member.updateRefreshToken(refreshToken);

        memberRepository.save(member);
    }
}
