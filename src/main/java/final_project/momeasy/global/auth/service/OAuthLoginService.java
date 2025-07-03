package final_project.momeasy.global.auth.service;

import final_project.momeasy.global.auth.dto.request.OAuthRequestDTO;
import final_project.momeasy.global.auth.dto.response.OAuthResponseDTO;

public interface OAuthLoginService {
    OAuthResponseDTO.OAuthLoginResponseDTO login(OAuthRequestDTO.OAuthLoginRequestDTO oAuthLoginRequestDTO);
}
