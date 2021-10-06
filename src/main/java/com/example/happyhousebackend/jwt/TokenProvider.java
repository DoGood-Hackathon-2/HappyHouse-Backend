package com.example.happyhousebackend.jwt;

import com.example.happyhousebackend.jwt.dto.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "auth";
    private static final Long ACCESS_TOKEN_VALID_TIME = 60 * 60 * 1L; // 1시간
    private static final Long REFRESH_TOKEN_VALID_TIME = 60 * 60 * 24 * 7L; // 7일

    private final String secret;
    private Key key;

    public TokenProvider(
            @Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        final byte[] kyeBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(kyeBytes);
    }

    public TokenDto createTokenDto(String uid) {
        return new TokenDto(
                createToken(uid, ACCESS_TOKEN_VALID_TIME),
                createToken(uid, REFRESH_TOKEN_VALID_TIME));
    }

    public String createToken(String uid, long tokenTime) {
        final Claims claims = Jwts.claims().setSubject(uid);
        final long now = (new Date()).getTime();
        final Date validity = new Date(now + tokenTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createToken(final Authentication authentication, final long validTime) {
        final String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        final long now = (new Date()).getTime();
        final Date validity = new Date(now + validTime);

        return Jwts.builder()
                .setSubject(authentication.getName())     // payload "sub": "name"
                .claim(AUTHORITIES_KEY, authorities)      // payload "auth": "ROLE_USER"
                .signWith(key, SignatureAlgorithm.HS512)  // header "alg": "HS512"
                .setIssuedAt(new Date())
                .setExpiration(validity)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        final Claims claims = extractClaim(token);
        final Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        final User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    private Claims extractClaim(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUid(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /* Token 유효성 검사 */
    public boolean validateToken(final String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            e.printStackTrace();
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            e.printStackTrace();
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            e.printStackTrace();
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = extractClaim(token).getExpiration();
        return expiration.before(new Date());
    }

}
