package final_project.momeasy.global.util.mail.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import final_project.momeasy.global.apiPayload.exception.CustomException;

public class MailException extends CustomException {
    public MailException(BaseErrorCode code) {
        super(code);
    }
}
