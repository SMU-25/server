package final_project.momeasy.global.apiPayload.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final BaseErrorCode code;

    public CustomException(BaseErrorCode errorcode) {this.code = errorcode;}
}
