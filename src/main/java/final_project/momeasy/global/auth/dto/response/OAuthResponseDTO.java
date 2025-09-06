package final_project.momeasy.global.auth.dto.response;

import lombok.Builder;

public class OAuthResponseDTO {

    @Builder
    public record OAuthLoginResponseDTO(
            String accessToken,
            String refreshToken
    ) {
    }
}
