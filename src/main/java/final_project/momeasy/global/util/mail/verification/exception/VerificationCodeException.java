package final_project.momeasy.global.util.mail.verification.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import final_project.momeasy.global.apiPayload.exception.CustomException;

public class VerificationCodeException extends CustomException {
    public VerificationCodeException(BaseErrorCode code) {
        super(code);
    }
}
