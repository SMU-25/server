package final_project.momeasy.domain.home_cam.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import final_project.momeasy.global.apiPayload.exception.CustomException;

public class HomecamException extends CustomException {
    public HomecamException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
