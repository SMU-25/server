package final_project.momeasy.domain.announcement.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import final_project.momeasy.global.apiPayload.exception.CustomException;

public class AnnouncementException extends CustomException {
    public AnnouncementException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
