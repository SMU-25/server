package final_project.momeasy.global.auth.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import final_project.momeasy.common.enums.SocialType;

public class OAuthRequestDTO {
    public record OAuthLoginRequestDTO(
            @JsonProperty("provider")
            SocialType provider,
            String accessToken
    ) {
    }

    public record ResetPasswordRequestDTO(
            String email
    ) {
    }
}
