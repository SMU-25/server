package final_project.momeasy.global.auth.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import final_project.momeasy.global.apiPayload.exception.CustomException;

public class AuthException extends CustomException {
    public AuthException(BaseErrorCode code) {
        super(code);
    }
}
