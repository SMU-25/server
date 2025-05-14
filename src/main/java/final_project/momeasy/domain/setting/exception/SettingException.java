package final_project.momeasy.domain.setting.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import final_project.momeasy.global.apiPayload.exception.CustomException;

public class SettingException extends CustomException {
    public SettingException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
