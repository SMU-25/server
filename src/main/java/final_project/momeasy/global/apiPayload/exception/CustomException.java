package final_project.momeasy.global.apiPayload.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final BaseErrorCode code;

    @Override
    public String getMessage() {
        return code.getMessage();
    }
}
