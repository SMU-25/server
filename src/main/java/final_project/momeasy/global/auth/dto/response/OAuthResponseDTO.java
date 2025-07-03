package final_project.momeasy.global.auth.dto.response;

public class OAuthResponseDTO {
    public record OAuthLoginResponseDTO(
            String accessToken,
            String refreshToken
    ) {
    }
}
