package final_project.momeasy.global.fcm.dto;

import final_project.momeasy.common.enums.DeviceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record FcmTokenRequest(
        @NotBlank String token,
        @NotNull DeviceType deviceType
) {}
