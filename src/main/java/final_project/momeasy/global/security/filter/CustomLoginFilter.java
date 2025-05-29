package final_project.momeasy.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import final_project.momeasy.domain.token.service.TokenService;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.security.CustomUserDetails;
import final_project.momeasy.global.security.dto.request.LoginRequestDTO;
import final_project.momeasy.global.security.dto.response.LoginResponseDTO;
import final_project.momeasy.global.security.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    @Override
    public Authentication attemptAuthentication(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response) throws AuthenticationException {

        log.info("[ LoginFilter ] 로그인 시도");
        ObjectMapper objectMapper = new ObjectMapper();
        LoginRequestDTO requestBody;

        try {
            requestBody = objectMapper.readValue(request.getInputStream(), LoginRequestDTO.class);
        } catch (IOException ex) {
            throw new AuthenticationServiceException("[ LoginFilter ] RequestBody 파싱 과정에서 오류 발생");
        }

        String email = requestBody.email();
        String password = requestBody.password();
        log.info("email: {}, password: {}", email, password);

        // 인증용 객체 생성
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);

        log.info("[ LoginFilter ] 인증용 객체가 생성되었습니다. 인증을 시도합니다.");

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain,
            @NonNull Authentication authResult) throws IOException, ServletException {

        log.info("[ LoginFilter ] 로그인에 성공했습니다.");

        // 로그인한 사용자 정보 꺼냄
        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();

        String accessToken = jwtUtil.generateJwtAccessToken(customUserDetails);
        String refreshToken = jwtUtil.generateRefreshToken(customUserDetails);

        // refresh token DB에 저장
        tokenService.saveOrUpdate(customUserDetails.getUsername(), refreshToken);

        // refresh token은 쿠키로 전달
        Cookie refreshTokenCookie = createCookie("refreshToken", refreshToken);
        response.addCookie(refreshTokenCookie);

        // Client에게 줄 Response build
        LoginResponseDTO loginResponse = LoginResponseDTO.builder()
                .accessToken(accessToken)
                .build();

        // Custom Response 작성
        CustomResponse<LoginResponseDTO> customResponse
                = CustomResponse.onSuccess(loginResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        // Body에 토큰을 담은 Response 작성
        objectMapper.writeValue(response.getWriter(), customResponse);

    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(24 * 60 * 60);

        return cookie;
    }

    @Override
    protected void unsuccessfulAuthentication(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull AuthenticationException failed) throws IOException, ServletException {

        log.info("[ LoginFilter ] 로그인에 실패했습니다.");

        String errorCode;
        String errorMessage;

        if (failed instanceof BadCredentialsException) {
            errorCode = "LOGIN401";
            errorMessage = "잘못된 정보입니다.";
        } else if (failed instanceof LockedException) {
            errorCode = "LOGIN423";
            errorMessage = "계정이 잠금 상태입니다.";
        } else if (failed instanceof DisabledException) {
            errorCode = "LOGIN403";
            errorMessage = "계정이 비활성화 되었습니다.";
        } else if (failed instanceof UsernameNotFoundException) {
            errorCode = "LOGIN404";
            errorMessage = "계정을 찾을 수 없습니다.";
        } else if (failed instanceof AuthenticationServiceException) {
            errorCode = "LOGIN400";
            errorMessage = "RequestBody 파싱 중 오류가 발생했습니다.";
        } else {
            errorCode = "LOGIN500";
            errorMessage = "인증에 실패했습니다.";
        }

        // Custom Response 생성 (data는 null로 설정)
        CustomResponse<Void> customResponse
                = CustomResponse.onFailure(HttpStatus.UNAUTHORIZED, errorCode, errorMessage, null);

        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        objectMapper.writeValue(response.getWriter(), customResponse);
    }
}
