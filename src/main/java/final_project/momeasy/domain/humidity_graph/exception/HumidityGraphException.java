package final_project.momeasy.domain.humidity_graph.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import final_project.momeasy.global.apiPayload.exception.CustomException;

public class HumidityGraphException extends CustomException {
    public HumidityGraphException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
