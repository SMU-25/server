package final_project.momeasy.domain.calendar.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import final_project.momeasy.global.apiPayload.exception.CustomException;

public class CalendarException extends CustomException {
    public CalendarException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
