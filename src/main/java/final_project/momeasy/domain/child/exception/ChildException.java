package final_project.momeasy.domain.child.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import final_project.momeasy.global.apiPayload.exception.CustomException;

public class ChildException extends CustomException {
    public ChildException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
