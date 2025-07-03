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
public class NaverOAuthApiClient implements SocialOAuthApiClient {

    private final RestTemplate restTemplate;


    @Override
    public boolean supports(SocialType provider) {
        return provider == SocialType.NAVER;
    }

    @Override
    public ParentProfile fetchProfile(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.GET,
                entity,
                Map.class
        );

        Map<String, Object> body = response.getBody();
        return OAuthAttributes.extract("naver", body);
    }
}
