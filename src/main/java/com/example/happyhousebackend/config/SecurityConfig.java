package com.example.happyhousebackend.config;

import com.example.happyhousebackend.config.auth.service.CustomOAuthUserService;
import com.example.happyhousebackend.config.auth.handler.OAuthSuccessHandler;
import com.example.happyhousebackend.jwt.JwtAccessDeniedHandler;
import com.example.happyhousebackend.jwt.JwtAuthenticationEntryPoint;
import com.example.happyhousebackend.jwt.JwtFilter;
import com.example.happyhousebackend.jwt.JwtSecurityConfig;
import com.example.happyhousebackend.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuthUserService customOAuthUserService;
    private final OAuthSuccessHandler oAuthSuccessHandler;
    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2-console/**", "favicon.ico");
    }

    /*
        0) SpringSecurity 기본 로그인 form
        1) REST API -> csrf disable
        2) 예외 처리 -> 커스텀(401, 403)
        3) h2-console 화면 사용
        4) 토큰 사용 -> 세션 사용 X
        5) URL별 권한 관리 설정 옵션 => URL, HTTP 메소드 별 관리
        6) JwtFilter 등록한 JwtSecurityConfig 클래스 적용
        7) 로그아웃 기능에 대한 진입점 -> 성공시 "/" 주소로 이동
        8) oauth2Login(): OAuth2 로그인 기능에 대한 설정의 진입점
           successHandler(): OAuth2 로그인 성공 후 처리할 Handler
           userInfoEndPoint(): OAuth2 로그인 성공 후 사용자 정보를 가져올 설정
           userService(): 소셜 로그인 성공 시 처리할 UserService 인터페이스의 구현체
    */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 0)
                .formLogin()

                // 1)
                .and()
                .csrf().disable()

                // 2)
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                // 3)
                .and()
                .headers().frameOptions().disable()

                // 4)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 5)
                .and()
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
                .antMatchers("/api/token/**").permitAll()
                .anyRequest().authenticated()

                // 6)
                .and()
                .apply(new JwtSecurityConfig(tokenProvider))

                // 7)
                .and()
                .logout()
                .logoutSuccessUrl("/")

                // 8)
                .and()
                .oauth2Login()
                .successHandler(oAuthSuccessHandler)
                .userInfoEndpoint()
                .userService(customOAuthUserService);

        http.addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
    }
}
