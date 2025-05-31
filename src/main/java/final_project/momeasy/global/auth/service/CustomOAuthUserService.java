package final_project.momeasy.global.auth.service;

import final_project.momeasy.common.enums.Role;
import final_project.momeasy.domain.parent.converter.ParentConverter;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.parent.repository.ParentRepository;
import final_project.momeasy.global.auth.dto.OAuthAttributes;
import final_project.momeasy.global.auth.dto.ParentProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuthUserService extends DefaultOAuth2UserService {

    private final ParentRepository parentRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // 1. 기본 서비스로 사용자 정보 요청
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("[CustomOAuthUserService] OAuth2User attributes: {}", oAuth2User.getAttributes());


        // 2. registrationId 확인 (어떤 플랫폼에서 로그인 했는지)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("[CustomOAuthUserService] 로그인 요청된 소셜 플랫폼: {}", registrationId);

        // 3. 사용자 정보 파싱
        Map<String, Object> attributes= oAuth2User.getAttributes();
        ParentProfile profile = OAuthAttributes.extract(registrationId, attributes);
        log.info("[CustomOAuthUserService] 파싱된 사용자 프로필: {}", profile);


        // 4. DB 저장 or 조회
        Parent parent = saveOrUpdate(profile);

        // 5. 인증 객체 반환 (USER) 권한 부여
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(Role.USER.name())),
                attributes,
                "id"
        );
    }

    private Parent saveOrUpdate(ParentProfile profile) {
        log.info("[CustomOAuthUserService] 이메일로 사용자 조회 중: {}", profile.getEmail());

        return parentRepository.findByEmail(profile.getEmail())
                .orElseGet(() -> {
                    log.info("[CustomOAuthUserService] 신규 사용자입니다. 회원 가입을 진행합니다.");
                    Parent newParent = ParentConverter.toParentFromProfile(profile);
                    return parentRepository.save(newParent);
                });
    }
}
