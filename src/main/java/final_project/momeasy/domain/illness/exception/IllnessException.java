package final_project.momeasy.domain.illness.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import final_project.momeasy.global.apiPayload.exception.CustomException;

public class IllnessException extends CustomException{
    public IllnessException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
