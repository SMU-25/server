package final_project.momeasy.domain.parent.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import final_project.momeasy.global.apiPayload.exception.CustomException;

public class ParentException extends CustomException {
    public ParentException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
