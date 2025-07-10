package final_project.momeasy.domain.symptom.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum SymptomErrorCode implements BaseErrorCode {

    UPDATE_FAILED(HttpStatus.BAD_REQUEST, "SYMPTOM400_1", "증상 정보를 수정할 수 없습니다."),
    DELETE_FAILED(HttpStatus.BAD_REQUEST, "SYMPTOM400_2", "증상 정보를 삭제할 수 없습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "SYMPTOM401", "증상에 접근할 수 있는 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "SYMPTOM404", "증상을 찾을 수 없습니다."),
    ALREADY_EXISTS(HttpStatus.CONFLICT, "SYMPTOM409", "이미 등록된 증상입니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SYMPTOM500", "증상 처리 중 서버 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
