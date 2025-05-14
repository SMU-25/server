package final_project.momeasy.domain.symptom.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import final_project.momeasy.global.apiPayload.exception.CustomException;

public class SymptomException extends CustomException {
    public SymptomException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
