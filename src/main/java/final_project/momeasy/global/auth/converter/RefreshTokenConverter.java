package final_project.momeasy.global.auth.converter;

import final_project.momeasy.global.auth.dto.response.RefreshTokenResponseDTO;

public class RefreshTokenConverter {

    public static RefreshTokenResponseDTO toRefreshTokenResponseDTO(
            String accessToken, String refreshToken
    ) {
        return RefreshTokenResponseDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
