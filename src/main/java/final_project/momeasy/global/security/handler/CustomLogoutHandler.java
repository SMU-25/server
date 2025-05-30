package final_project.momeasy.global.security.handler;

import final_project.momeasy.domain.token.service.TokenService;
import final_project.momeasy.global.security.jwt.JwtUtil;
import final_project.momeasy.global.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        log.info("[ LogoutHandler ] 로그아웃 요청");

        // access token 추출
        String accessToken = jwtUtil.extractAccessToken(request);

        if (accessToken != null) {
            try {
                jwtUtil.validateToken(accessToken);
                // TODO: access token 블랙리스팅
            } catch (Exception e) {
                log.warn("[ LogoutHandler ] access token 유효성 검증 실패: {}", e.getMessage());
            }
        }

        // refresh token 추출
        String refreshToken = CookieUtil.getCookieValue(request, "refreshToken");

        // refresh token 삭제
        if (refreshToken != null) {
            try {
                jwtUtil.validateToken(refreshToken);
                // DB에 저장된 refresh token 삭제
                tokenService.deleteByEmail(jwtUtil.getEmail(refreshToken));
                log.info("[ LogoutHandler ] DB에 저장된 refresh token 삭제 완료");
            } catch (Exception e) {
                log.warn("[ LogoutHandler ] refresh token 유효성 검증 실패: {}", e.getMessage());
            }
        }

        // cookie에서 refresh token 삭제
        CookieUtil.deleteRefreshTokenFromCookie(response);
        log.info("[ LogoutHandler ] 쿠키 삭제 완료");
    }

}
