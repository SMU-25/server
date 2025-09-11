package final_project.momeasy.domain.setting.controller;

import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.setting.dto.SettingRequest;
import final_project.momeasy.domain.setting.dto.SettingResponse;
import final_project.momeasy.domain.setting.service.SettingService;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.security.annotation.AuthParent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
@Tag(name = "Setting", description = "설정 API by 정현")
public class SettingController {

    private final SettingService settingService;

    @Operation(summary = "부모의 알림 설정 조회")
    @GetMapping
    public CustomResponse<SettingResponse> getSettings(@AuthParent Parent parent) {
        SettingResponse response = settingService.getSetting(parent);
        return CustomResponse.onSuccess(HttpStatus.OK, response);
    }

    @Operation(summary = "부모의 알림 설정 변경")
    @PatchMapping
    public CustomResponse<Void> updateSettings(@AuthParent Parent parent, @RequestBody SettingRequest request) {
        settingService.updateSetting(parent, request);
        return CustomResponse.onSuccess(HttpStatus.OK, null);
    }
}
