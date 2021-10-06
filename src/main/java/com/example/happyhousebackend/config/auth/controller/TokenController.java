package com.example.happyhousebackend.config.auth.controller;

import com.example.happyhousebackend.config.auth.service.CustomOAuthUserService;
import com.example.happyhousebackend.global.message.JwtMessage;
import com.example.happyhousebackend.jwt.dto.TokenDto;
import com.example.happyhousebackend.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TokenController {

    private final TokenProvider tokenProvider;
    private final CustomOAuthUserService customOAuthUserService;

    @GetMapping("/token/refresh")
    public TokenDto refreshToken(HttpServletRequest request, HttpServletResponse response) {
        final String refreshToken = request.getHeader(JwtMessage.REFRESH_TOKEN_HEADER);

        if (refreshToken != null && tokenProvider.validateToken(refreshToken)) {
            final String email = tokenProvider.getUid(refreshToken);
            final TokenDto newToken = tokenProvider.createTokenDto(email);
            response.addHeader(JwtMessage.ACCESS_TOKEN_HEADER, newToken.getAccessToken());
            response.addHeader(JwtMessage.REFRESH_TOKEN_HEADER, newToken.getRefreshToken());
            response.setContentType("application/json;charset=UTF-8");
            customOAuthUserService.updateRefreshToken(email, newToken.getRefreshToken());

            return newToken;
        }
        return null;
    }

}
