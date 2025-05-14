package final_project.momeasy.domain.fever_record.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import final_project.momeasy.global.apiPayload.exception.CustomException;

public class FeverRecordException extends CustomException {
    public FeverRecordException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
