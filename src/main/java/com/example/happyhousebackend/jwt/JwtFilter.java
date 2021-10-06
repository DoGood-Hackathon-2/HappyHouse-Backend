package com.example.happyhousebackend.jwt;

import com.example.happyhousebackend.global.message.JwtMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/* Request에서 전달된 JWT 토큰 검증 */
@Slf4j
public class JwtFilter extends GenericFilterBean {

    private final TokenProvider tokenProvider;

    public JwtFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    /* JWT 토큰의 인증정보를 SecurityContext에 저장 */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        final String jwtToken = resolveToken(httpServletRequest);
        final String requestUri = httpServletRequest.getRequestURI();

        if (StringUtils.hasText(jwtToken) && tokenProvider.validateToken(jwtToken)) {
            final Authentication authentication = tokenProvider.getAuthentication(jwtToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Save '{}' Authentication to SecurityContextHolder, uri: {}", authentication.getName(), requestUri);
        } else {
            log.debug("Jwt Token is not exist, uri: {}", requestUri);
        }
        chain.doFilter(request, response);
    }

    /* Request Header에서 Token 정보 꺼내오기 */
    private String resolveToken(HttpServletRequest httpServletRequest) {
        final String bearerToken = httpServletRequest.getHeader(JwtMessage.ACCESS_TOKEN_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
