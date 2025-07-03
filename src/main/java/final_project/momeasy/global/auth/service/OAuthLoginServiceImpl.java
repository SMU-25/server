package final_project.momeasy.global.auth.service;

import final_project.momeasy.domain.parent.converter.ParentConverter;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.parent.repository.ParentRepository;
import final_project.momeasy.domain.token.service.TokenService;
import final_project.momeasy.global.auth.client.SocialOAuthApiClient;
import final_project.momeasy.global.auth.dto.ParentProfile;
import final_project.momeasy.global.auth.dto.request.OAuthRequestDTO;
import final_project.momeasy.global.auth.dto.response.OAuthResponseDTO;
import final_project.momeasy.global.security.CustomUserDetails;
import final_project.momeasy.global.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthLoginServiceImpl implements OAuthLoginService {

    private final List<SocialOAuthApiClient> socialOAuthApiClients;
    private final ParentRepository parentRepository;
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    public OAuthResponseDTO.OAuthLoginResponseDTO login(OAuthRequestDTO.OAuthLoginRequestDTO oAuthLoginRequestDTO) {

        // API Client 찾기
        SocialOAuthApiClient client = socialOAuthApiClients.stream()
                .filter(c -> c.supports(oAuthLoginRequestDTO.provider()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("지원하지 않는 소셜 로그인입니다: " + oAuthLoginRequestDTO.provider()));

        // 사용자 정보 요청
        ParentProfile profile = client.fetchProfile(oAuthLoginRequestDTO.accessToken());

        // 기존 유저 조회 or 회원가입
        log.info("[ OAuthLoginServiceImpl ] 이메일로 사용자 조회 중: {}", profile.getEmail());

        Parent parent = parentRepository.findByEmail(profile.getEmail())
                .orElseGet(() -> {
                    log.info("[ OAuthLoginServiceImpl ] 신규 사용자입니다. 회원 가입을 진행합니다.");
                    return parentRepository.save(ParentConverter.toParentFromProfile(profile));
                });

        // JWT 발급
        CustomUserDetails userDetails = new CustomUserDetails(parent);
        String accessJwt = jwtUtil.generateJwtAccessToken(userDetails);
        String refreshJwt = jwtUtil.generateRefreshToken(userDetails);

        tokenService.saveOrUpdate(parent.getEmail(), refreshJwt);

        return new OAuthResponseDTO.OAuthLoginResponseDTO(accessJwt, refreshJwt);
    }
}
