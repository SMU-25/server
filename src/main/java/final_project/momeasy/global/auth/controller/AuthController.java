package final_project.momeasy.global.auth.controller;

import final_project.momeasy.domain.parent.dto.request.ParentRequestDTO;
import final_project.momeasy.domain.parent.dto.response.ParentResponseDTO;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.parent.exception.ParentErrorCode;
import final_project.momeasy.domain.parent.exception.ParentException;
import final_project.momeasy.domain.parent.repository.ParentRepository;
import final_project.momeasy.domain.parent.service.command.ParentCommandService;
import final_project.momeasy.domain.token.entity.Token;
import final_project.momeasy.domain.token.service.TokenService;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.auth.converter.RefreshTokenConverter;
import final_project.momeasy.global.auth.dto.request.OAuthRequestDTO;
import final_project.momeasy.global.auth.dto.request.RefreshTokenRequestDTO;
import final_project.momeasy.global.auth.dto.response.OAuthResponseDTO;
import final_project.momeasy.global.auth.dto.response.RefreshTokenResponseDTO;
import final_project.momeasy.global.auth.service.OAuthLoginService;
import final_project.momeasy.global.auth.service.PasswordResetService;
import final_project.momeasy.global.security.CustomUserDetails;
import final_project.momeasy.global.security.dto.request.LoginRequestDTO;
import final_project.momeasy.global.security.jwt.JwtUtil;
import final_project.momeasy.global.security.jwt.exception.JwtErrorCode;
import final_project.momeasy.global.security.jwt.exception.JwtException;
import final_project.momeasy.global.util.CookieUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "인증 관련 API by 현빈")
public class AuthController {

    private final ParentCommandService parentCommandService;
    private final OAuthLoginService oAuthLoginService;
    private final PasswordResetService passwordResetService;
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;
    private final ParentRepository parentRepository;

    @Operation(summary = "로컬 회원가입")
    @PostMapping("/signup")
    public CustomResponse<ParentResponseDTO.ParentCreateResponseDTO> localSignUp(
            @RequestBody ParentRequestDTO.ParentCreateRequestDTO createDTO) {
        return CustomResponse.onSuccess(HttpStatus.CREATED, parentCommandService.createParent(createDTO));
    }

    // Swagger test용 dummy controller
    @Operation(summary = "로컬 로그인")
    @PostMapping("/login")
    public CustomResponse<?> localLogin(@RequestBody LoginRequestDTO loginDTO) {
        return null;
    }

    // Swagger test용 dummy controller
    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public CustomResponse<?> logout() {
        return null;
    }

    @Operation(summary = "소셜 로그인 (KAKAO, NAVER)")
    @PostMapping("/social-login")
    public CustomResponse<OAuthResponseDTO.OAuthLoginResponseDTO> socialLogin(
            @RequestBody OAuthRequestDTO.OAuthLoginRequestDTO requestDTO,
            HttpServletResponse response) {

        OAuthResponseDTO.OAuthLoginResponseDTO responseDTO = oAuthLoginService.login(requestDTO);

        String email = jwtUtil.getEmail(responseDTO.accessToken());
        String refreshToken = tokenService.findByEmail(email)
                .orElseThrow(() -> new JwtException(JwtErrorCode.MISSING_TOKEN))
                .getRefreshToken();

        CookieUtil.addRefreshTokenToCookie(response, refreshToken);

        return CustomResponse.onSuccess(responseDTO);
    }

    @Operation(summary = "비밀번호 재설정 (임시 비밀번호 메일 발급)")
    @PostMapping("/reset-password")
    public CustomResponse<String> resetPassword(
            @RequestBody OAuthRequestDTO.ResetPasswordRequestDTO requestDTO
    ) {
        passwordResetService.resetPassword(requestDTO.email());
        return CustomResponse.onSuccess(HttpStatus.OK, "임시 비밀번호가 메일로 전송되었습니다.");
    }

    @Operation(summary = "액세스 토큰 재발급 (리프레시 토큰 이용)")
    @PostMapping("/refresh")
    public CustomResponse<RefreshTokenResponseDTO> refreshToken(
            @RequestBody RefreshTokenRequestDTO requestDTO
    ) {
        Token token = tokenService.findByRefreshToken(requestDTO.refreshToken())
                .orElseThrow(() -> new JwtException(JwtErrorCode.INVALID_TOKEN));

        Parent parent = parentRepository.findByEmail(token.getEmail())
                .orElseThrow(() -> new ParentException(ParentErrorCode.NOT_FOUND));

        String newAccessToken = jwtUtil.generateJwtAccessToken(new CustomUserDetails(parent));

        return CustomResponse.onSuccess(RefreshTokenConverter.toRefreshTokenResponseDTO(newAccessToken, requestDTO.refreshToken()));

    }
}
