package final_project.momeasy.global.security.dto.response;

import lombok.Builder;

@Builder
public record LoginResponseDTO(
        String accessToken,
        String refreshToken
) {
}
