package final_project.momeasy.global.fcm.controller;

import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.fcm.dto.FcmTokenRequest;
import final_project.momeasy.global.fcm.service.FcmTokenService;
import final_project.momeasy.global.security.CustomUserDetails;
import final_project.momeasy.global.security.annotation.AuthParent;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/fcm-token")
@RequiredArgsConstructor
@Tag(name = "FcmToken", description = "FcmToken API by 정현")
public class FcmTokenController {

    private final FcmTokenService tokenService;

    @PostMapping
    public CustomResponse<String> saveToken(
            @AuthParent CustomUserDetails customUserDetails,
            @Valid @RequestBody FcmTokenRequest request
    ) {
        tokenService.saveToken(customUserDetails.getParent(), request.token(), request.deviceType());
        return CustomResponse.onSuccess(HttpStatus.CREATED, "FCM 토큰 저장 완료");
    }

    @DeleteMapping
    public CustomResponse<String> deleteToken(
            @AuthParent CustomUserDetails customUserDetails,
            @RequestParam String token
    ) {
        tokenService.deleteToken(customUserDetails.getParent(), token);
        return CustomResponse.onSuccess(HttpStatus.OK, "FCM 토큰 삭제 완료");
    }
}
