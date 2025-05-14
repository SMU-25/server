package final_project.momeasy.domain.illness.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum IllnessErrorCode implements BaseErrorCode {
    UPDATE_FAILED(HttpStatus.BAD_REQUEST, "ILLNESS400_1", "질환 정보를 수정할 수 없습니다"),
    DELETE_FAILED(HttpStatus.BAD_REQUEST, "ILLNESS400_2", "질환 정보를 삭제할 수 없습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "ILLNESS401", "질환에 접근할 수 있는 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "ILLNESS404", "질환를 찾을 수 없습니다."),
    ALREADY_ADD(HttpStatus.CONFLICT, "ILLNESS409", "이미 등록된 캘린더입니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "ILLNESS500", "질환 처리 중 서버 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
