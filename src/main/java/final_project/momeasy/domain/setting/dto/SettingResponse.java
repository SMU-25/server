package final_project.momeasy.domain.setting.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SettingResponse {
    private Boolean allAlarm;
    private Boolean careAlarm;
    private Boolean marketingAlarm;
}