package final_project.momeasy.global.security.jwt;

import final_project.momeasy.global.config.JwtProperties;
import final_project.momeasy.global.security.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtUtil {

    private final Long accessExpMs;
    private final Long refreshExpMs;
    private final SecretKey secretKey;

    public JwtUtil(JwtProperties jwtProperties) {
        this.accessExpMs = jwtProperties.getToken().getAccessExpirationTime();
        this.refreshExpMs = jwtProperties.getToken().getRefreshExpirationTime();

        byte[] keyBytes = Base64.getDecoder().decode(jwtProperties.getSecretKey());
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 토큰의 Subject로부터 사용자 Email을 추출하는 메서드
    public String getEmail(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // Token 발급
    public String generateToken(CustomUserDetails customUserDetails, Instant expirationTime) {

        log.info("[ JwtUtil ] 토큰을 생성합니다.");

        // 현재 시간
        Instant issuedAt = Instant.now();

        // 토큰에 부여할 권한
        String authorities = customUserDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .header()
                .add("type", "JWT")
                .and()
                .subject(customUserDetails.getUsername())
                .claim("role", authorities)
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expirationTime))
                .signWith(secretKey)
                .compact();
    }

    // Jwt Access Token 생성
    public String generateJwtAccessToken(CustomUserDetails customUserDetails) {
        Instant expirationTime = Instant.now().plusMillis(accessExpMs);

        return generateToken(customUserDetails, expirationTime);
    }

    // Jwt Refresh Token 생성
    public String generateRefreshToken(CustomUserDetails customUserDetails) {
        Instant expirationTime = Instant.now().plusMillis(refreshExpMs);
        return generateToken(customUserDetails, expirationTime);
    }

    // 헤더에서 토큰 추출
    public String extractAccessToken(HttpServletRequest request) {
        log.info("[ JwtUtil ] 헤더에서 토큰을 추출합니다.");

        String tokenFromHeader = request.getHeader("Authorization");

        if (tokenFromHeader == null || !tokenFromHeader.startsWith("Bearer ")) {
            log.warn("[ JwtUtil ] Header에 토큰이 존재하지 않습니다.");
            return null;
        }

        return tokenFromHeader.split(" ")[1];   // Bearer와 분리하여 토큰 추출
    }

    // 쿠키에서 refreshToken 추출
    public String extractRefreshToken(HttpServletRequest request) {
        log.info("[ JwtUtil ] 쿠키에서 refresh token을 추출합니다.");

        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("refreshToken")) {
                return cookie.getValue();
            }
        }

        log.warn("[ JwtUtil ] 쿠키에서 refresh token을 찾을 수 없습니다.");
        return null;
    }

    // Token 유효성 검증
    public Claims validateToken(String token) {
        log.info("[ JwtUtil ] 토큰의 유효성을 검증합니다.");

        try {
            long seconds = 3 * 60; // 3분 오차 허용
            return Jwts.parser()
                    .clockSkewSeconds(seconds)
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (ExpiredJwtException e) {
            log.warn("[ JwtUtil ] 만료된 JWT 토큰입니다");
            throw e;
        } catch (SecurityException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            log.error("[ JwtUtil ] 잘못된 JWT 토큰입니다.", e);
            throw new SecurityException("잘못된 토큰입니다");
        }
    }

}
