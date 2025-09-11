package final_project.momeasy.global.security.filter;

import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.parent.exception.ParentErrorCode;
import final_project.momeasy.domain.parent.exception.ParentException;
import final_project.momeasy.domain.parent.repository.ParentRepository;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.security.CustomUserDetails;
import final_project.momeasy.global.security.jwt.JwtUtil;
import final_project.momeasy.global.security.jwt.exception.JwtErrorCode;
import final_project.momeasy.global.util.ResponseUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ParentRepository parentRepository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String uri = request.getRequestURI();
        return uri.startsWith("/api/auth/login")
                || uri.startsWith("/api/auth/social-login")
                || uri.startsWith("/api/auth/refresh")
                || uri.equals("/api/auth/signup")
                || uri.equals("/api/auth/reset-password");
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        log.info("[ JwtAuthorizationFilter ] 인가 필터 작동");

        try {
            // 헤더에서 access token 추출
            String accessToken = jwtUtil.extractAccessToken(request);

            // access token이 없다면 다음 필터로 이동
            if (accessToken == null) {
                log.warn("[ JwtAuthorizationFilter ] access token이 존재하지 않습니다.");
                filterChain.doFilter(request, response);
                return;
            }

            // 토큰 인증
            authenticateAccessToken(accessToken);

            log.info("[ JwtAuthorizationFilter ] 종료. 다음 필터로 이동");
        } catch (ExpiredJwtException e) {
            log.warn("[ JwtAuthorizationFilter ] access token 만료");
            CustomResponse<Void> errorResponse = CustomResponse.onFailure(
                    JwtErrorCode.EXPIRED_TOKEN, null);

            ResponseUtil.writeJsonResponse(response, errorResponse, HttpStatus.UNAUTHORIZED);
        } catch (JwtException e) {
            log.warn("[ JwtAuthorizationFilter ] 잘못된 access token");
            CustomResponse<Void> errorResponse = CustomResponse.onFailure(
                    JwtErrorCode.INVALID_TOKEN, null);

            ResponseUtil.writeJsonResponse(response, errorResponse, HttpStatus.UNAUTHORIZED);
        }
        filterChain.doFilter(request, response);
    }


    private void authenticateAccessToken(String accessToken) {
        log.info("[ JwtAuthorizationFilter ] 인가 과정 시작");

        // access token 유효성 검증
        jwtUtil.validateToken(accessToken);
        log.info("[ JwtAuthorizationFilter ] access token 유효성 검증");

        // 이메일 추출
        String email = jwtUtil.getEmail(accessToken);

        // DB에서 parent 조회
        Parent parent = parentRepository.findByEmail(email)
                .orElseThrow(() -> new ParentException(ParentErrorCode.NOT_FOUND));

        // CustomUserDetail 객체 생성
        CustomUserDetails customUserDetails = new CustomUserDetails(parent);

        log.info("[ JwtAuthorizationFilter ] UserDetails 객체 생성 성공");

        // Spring Security 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                customUserDetails,
                null,
                customUserDetails.getAuthorities()
        );

        // SecurityContextHolder에 현재 인증 객체 저장
        SecurityContextHolder.getContext().setAuthentication(authToken);

        log.info("[ JwtAuthorizationFilter ] 인증 완료: {}", email);
    }
}
