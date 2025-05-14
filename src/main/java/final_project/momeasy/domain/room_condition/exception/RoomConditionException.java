package final_project.momeasy.domain.room_condition.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import final_project.momeasy.global.apiPayload.exception.CustomException;

public class RoomConditionException extends CustomException {
    public RoomConditionException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
