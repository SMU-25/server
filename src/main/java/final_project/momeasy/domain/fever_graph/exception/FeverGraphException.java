package final_project.momeasy.domain.fever_graph.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import final_project.momeasy.global.apiPayload.exception.CustomException;

public class FeverGraphException extends CustomException {
    public FeverGraphException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
