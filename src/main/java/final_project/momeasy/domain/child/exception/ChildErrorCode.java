package final_project.momeasy.domain.child.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ChildErrorCode implements BaseErrorCode {
    UPDATE_FAILED(HttpStatus.BAD_REQUEST, "CHILD400_1", "아이 정보를 수정할 수 없습니다"),
    DELETE_FAILED(HttpStatus.BAD_REQUEST, "CHILD400_2", "아이 정보를 삭제할 수 없습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "CHILD401", "아이에 접근할 수 있는 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "CHILD404", "아이를 찾을 수 없습니다."),
    ALREADY_ADDED(HttpStatus.CONFLICT, "CHILD409", "이미 등록된 아이입니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "CHILD500", "아이 처리 중 서버 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
