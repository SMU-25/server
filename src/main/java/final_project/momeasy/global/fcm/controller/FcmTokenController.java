package final_project.momeasy.global.fcm.controller;

import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.fcm.dto.FcmTokenRequest;
import final_project.momeasy.global.fcm.service.FcmTokenService;
import final_project.momeasy.global.security.annotation.AuthParent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fcm-token")
@Tag(name = "FcmToken", description = "파이어베이스 API by 정현")
@RequiredArgsConstructor
public class FcmTokenController {

    private final FcmTokenService tokenService;

    @Operation(summary = "FCM 토큰 저장")
    @PostMapping
    public CustomResponse<String> saveToken(
            @AuthParent Parent parent,
            @Valid @RequestBody FcmTokenRequest request
    ) {
        tokenService.saveToken(parent, request.token(), request.deviceType());
        return CustomResponse.onSuccess(HttpStatus.CREATED, "FCM 토큰 저장 완료");
    }

    @Operation(summary = "FCM 토큰 삭제")
    @DeleteMapping
    public CustomResponse<String> deleteToken(
            @AuthParent Parent parent,
            @RequestParam String token
    ) {
        tokenService.deleteToken(parent, token);
        return CustomResponse.onSuccess(HttpStatus.OK, "FCM 토큰 삭제 완료");
    }
}
