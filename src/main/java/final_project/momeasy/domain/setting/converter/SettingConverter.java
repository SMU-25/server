package final_project.momeasy.domain.setting.converter;

import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.setting.dto.SettingRequest;
import final_project.momeasy.domain.setting.dto.SettingResponse;
import final_project.momeasy.domain.setting.entity.Setting;

public class SettingConverter {

    public static Setting toEntity(SettingRequest request, Parent parent) {
        return Setting.builder()
                .all_alarm(request.getAllAlarm())
                .care_alarm(request.getCareAlarm())
                .marketing_alarm(request.getMarketingAlarm())
                .parent(parent)
                .build();
    }

    public static SettingResponse toResponse(Setting setting) {
        return SettingResponse.builder()
                .allAlarm(setting.getAll_alarm())
                .careAlarm(setting.getCare_alarm())
                .marketingAlarm(setting.getMarketing_alarm())
                .build();
    }
}
