package com.example.happyhousebackend.config.auth.handler;

import com.example.happyhousebackend.config.auth.dto.OAuthAttribute;
import com.example.happyhousebackend.config.auth.dto.UserDto;
import com.example.happyhousebackend.config.auth.service.CustomOAuthUserService;
import com.example.happyhousebackend.global.message.JwtMessage;
import com.example.happyhousebackend.jwt.TokenProvider;
import com.example.happyhousebackend.jwt.dto.TokenDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;
    private final CustomOAuthUserService customOAuthUserService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {
        final OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        final String socialType = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
        final UserDto userDto = UserDto.toDto(oAuth2User);
        final OAuthAttribute attribute = OAuthAttribute.of(socialType, "sub", oAuth2User.getAttributes());
        final TokenDto tokenDto = tokenProvider.createTokenDto(userDto.getEmail());

        log.info("{}", tokenDto);

        customOAuthUserService.saveOrUpdateMember(attribute, tokenDto.getRefreshToken());
        responseJwtToken(response, tokenDto);
    }

    private void responseJwtToken(HttpServletResponse response, TokenDto tokenDto) throws IOException {
        response.addHeader(JwtMessage.ACCESS_TOKEN_HEADER, tokenDto.getAccessToken());
        response.addHeader(JwtMessage.REFRESH_TOKEN_HEADER, tokenDto.getRefreshToken());
        response.setContentType("application/json;charset=UTF-8");

        PrintWriter writer = response.getWriter();
        writer.println(objectMapper.writeValueAsString(tokenDto));
        writer.flush();
    }
}
