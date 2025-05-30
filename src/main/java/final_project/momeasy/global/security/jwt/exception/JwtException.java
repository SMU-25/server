package final_project.momeasy.global.security.jwt.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import final_project.momeasy.global.apiPayload.exception.CustomException;

public class JwtException extends CustomException {
    public JwtException(BaseErrorCode code) {
        super(code);
    }
}
