package final_project.momeasy.global.auth.dto.response;

import lombok.Builder;

@Builder
public record RefreshTokenResponseDTO(
        String accessToken,
        String refreshToken
) {
}
