package final_project.momeasy.global.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import final_project.momeasy.domain.token.service.TokenService;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.auth.exception.AuthErrorCode;
import final_project.momeasy.global.security.CustomUserDetails;
import final_project.momeasy.global.security.dto.request.LoginRequestDTO;
import final_project.momeasy.global.security.dto.response.LoginResponseDTO;
import final_project.momeasy.global.security.jwt.JwtUtil;
import final_project.momeasy.global.util.CookieUtil;
import final_project.momeasy.global.util.ResponseUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

        // Client에게 줄 Response build
        LoginResponseDTO loginResponse = LoginResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        // Custom Response 작성
        CustomResponse<LoginResponseDTO> customResponse
                = CustomResponse.onSuccess(loginResponse);

        ResponseUtil.writeJsonResponse(response, customResponse, HttpStatus.OK);

    }


    @Override
    protected void unsuccessfulAuthentication(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull AuthenticationException failed) throws IOException, ServletException {

        log.info("[ LoginFilter ] 로그인에 실패했습니다.");

        AuthErrorCode authError;

        if (failed instanceof BadCredentialsException) {
            authError = AuthErrorCode.WRONG_CREDENTIALS;
        } else if (failed instanceof LockedException) {
            authError = AuthErrorCode.ACCOUNT_LOCKED;
        } else if (failed instanceof DisabledException) {
            authError = AuthErrorCode.ACCOUNT_DISABLED;
        } else if (failed instanceof UsernameNotFoundException) {
            authError = AuthErrorCode.OAUTH_EMAIL_NOT_FOUND;
        } else if (failed instanceof AuthenticationServiceException) {
            if (failed.getMessage().contains("소셜 로그인")) {
                authError = AuthErrorCode.SOCIAL_USER_CANNOT_LOGIN;
            } else {
                authError = AuthErrorCode.OAUTH_TOKEN_FAIL;
            }
        } else {
            authError = AuthErrorCode.LOGIN_FAILED;
        }

        // Custom Response 생성 (data는 null로 설정)
        CustomResponse<Void> customResponse
                = CustomResponse.onFailure(authError.getStatus(), authError.getCode(), authError.getMessage(), null);

        ResponseUtil.writeJsonResponse(response, customResponse, HttpStatus.UNAUTHORIZED);
    }
}
