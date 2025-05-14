package final_project.momeasy.domain.notification.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import final_project.momeasy.global.apiPayload.exception.CustomException;

public class NotificationException extends CustomException {
    public NotificationException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
