package final_project.momeasy.domain.outing_record.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import final_project.momeasy.global.apiPayload.exception.CustomException;

public class OutingRecordException extends CustomException {
    public OutingRecordException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
