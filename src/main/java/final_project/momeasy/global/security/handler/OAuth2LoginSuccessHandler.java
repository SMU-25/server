package final_project.momeasy.global.security.handler;

import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.parent.exception.ParentErrorCode;
import final_project.momeasy.domain.parent.exception.ParentException;
import final_project.momeasy.domain.parent.repository.ParentRepository;
import final_project.momeasy.domain.token.service.TokenService;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.auth.dto.OAuthAttributes;
import final_project.momeasy.global.auth.dto.ParentProfile;
import final_project.momeasy.global.security.CustomUserDetails;
import final_project.momeasy.global.security.dto.response.LoginResponseDTO;
import final_project.momeasy.global.security.jwt.JwtUtil;
import final_project.momeasy.global.util.CookieUtil;
import final_project.momeasy.global.util.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final ParentRepository parentRepository;
    private final TokenService tokenService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        String registrationId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();

        ParentProfile profile = OAuthAttributes.extract(registrationId, oAuth2User.getAttributes());

        String email = profile.getEmail();
        log.info("[ OAuth2LoginSuccessHandler ] email: {}", email);

        Parent parent = parentRepository.findByEmail(email)
                .orElseThrow(() -> new ParentException(ParentErrorCode.NOT_FOUND));

        CustomUserDetails userDetails = new CustomUserDetails(parent);

        String accessToken = jwtUtil.generateJwtAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        // refresh token DB에 저장
        tokenService.saveOrUpdate(userDetails.getUsername(), refreshToken);

        // refresh token은 쿠키로 전달
        CookieUtil.addRefreshTokenToCookie(response, refreshToken);

        // Client에게 줄 Response build
        LoginResponseDTO loginResponse = LoginResponseDTO.builder()
                .accessToken(accessToken)
                .build();

        // Custom Response 작성
        CustomResponse<LoginResponseDTO> customResponse
                = CustomResponse.onSuccess(loginResponse);

        ResponseUtil.writeJsonResponse(response, customResponse, HttpStatus.OK);

    }
}
