package final_project.momeasy.global.security;

import final_project.momeasy.common.enums.SocialType;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.parent.repository.ParentRepository;
import final_project.momeasy.global.auth.exception.AuthErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final ParentRepository parentRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("[ CustomUserDetailsService loadUserByUsername() ] email을 이용하여 user를 검색합니다.");

        Parent parent = parentRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(AuthErrorCode.WRONG_ID.getMessage()));

        // 소셜 로그인 유저일 경우 로그인 불가 예외
        if (parent.getSocialType() != SocialType.LOCAL) {
            throw new AuthenticationServiceException(AuthErrorCode.SOCIAL_USER_CANNOT_LOGIN.getMessage());
        }

        return new CustomUserDetails(parent);
    }
}
