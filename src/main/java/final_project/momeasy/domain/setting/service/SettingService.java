package final_project.momeasy.domain.setting.service;

import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.setting.dto.SettingRequest;
import final_project.momeasy.domain.setting.dto.SettingResponse;

public interface SettingService {

    SettingResponse getSetting(Parent parent);

    void updateSetting(Parent parent, SettingRequest request);
}
