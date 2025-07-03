package final_project.momeasy.global.auth.client;

import final_project.momeasy.common.enums.SocialType;
import final_project.momeasy.global.auth.dto.OAuthAttributes;
import final_project.momeasy.global.auth.dto.ParentProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class KakaoOAuthApiClient implements SocialOAuthApiClient {

    private final RestTemplate restTemplate;

    @Override
    public boolean supports(SocialType provider) {
        return provider == SocialType.KAKAO;
    }

    @Override
    public ParentProfile fetchProfile(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.GET,
                entity,
                Map.class
        );

        return OAuthAttributes.extract("kakao", response.getBody());
    }
}
