package final_project.momeasy.domain.setting.service;

import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.setting.converter.SettingConverter;
import final_project.momeasy.domain.setting.dto.SettingRequest;
import final_project.momeasy.domain.setting.dto.SettingResponse;
import final_project.momeasy.domain.setting.entity.Setting;
import final_project.momeasy.domain.setting.exception.SettingErrorCode;
import final_project.momeasy.domain.setting.exception.SettingException;
import final_project.momeasy.domain.setting.repository.SettingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SettingServiceImpl implements SettingService {

    private final SettingRepository settingRepository;

    @Override
    public SettingResponse getSetting(Parent parent) {
        Setting setting = settingRepository.findByParentId(parent.getId())
                .orElseThrow(() -> new SettingException(SettingErrorCode.NOT_FOUND));

        return SettingConverter.toResponse(setting);
    }

    @Override
    public void updateSetting(Parent parent, SettingRequest request) {
        Setting setting = settingRepository.findByParentId(parent.getId())
                .orElseThrow(() -> new SettingException(SettingErrorCode.NOT_FOUND));

        if (Boolean.FALSE.equals(request.getAllAlarm())) {
            // 전체 알림 OFF → 나머지도 OFF
            setting.setAll_alarm(false);
            setting.setCare_alarm(false);
            setting.setMarketing_alarm(false);
        } else {
            // 전체 알림 ON → 하위 설정 반영
            setting.setAll_alarm(true);
            setting.setCare_alarm(request.getCareAlarm());
            setting.setMarketing_alarm(request.getMarketingAlarm());
        }
    }
}
