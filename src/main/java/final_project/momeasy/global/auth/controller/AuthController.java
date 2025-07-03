package final_project.momeasy.global.auth.controller;

import final_project.momeasy.domain.parent.dto.request.ParentRequestDTO;
import final_project.momeasy.domain.parent.dto.response.ParentResponseDTO;
import final_project.momeasy.domain.parent.service.command.ParentCommandService;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.auth.dto.request.OAuthRequestDTO;
import final_project.momeasy.global.auth.dto.response.OAuthResponseDTO;
import final_project.momeasy.global.auth.service.OAuthLoginService;
import final_project.momeasy.global.security.dto.request.LoginRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Auth", description = "인증 관련 API")
public class AuthController {

    private final ParentCommandService parentCommandService;
    private final OAuthLoginService oAuthLoginService;

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
    @PostMapping("/social")
    public CustomResponse<OAuthResponseDTO.OAuthLoginResponseDTO> socialLogin(
            @RequestBody OAuthRequestDTO.OAuthLoginRequestDTO requestDTO) {
        return CustomResponse.onSuccess(oAuthLoginService.login(requestDTO));
    }
}
