package final_project.momeasy.domain.temperature_graph.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import final_project.momeasy.global.apiPayload.exception.CustomException;

public class TemperatureGraphException extends CustomException {
    public TemperatureGraphException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
