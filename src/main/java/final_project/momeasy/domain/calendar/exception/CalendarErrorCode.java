package final_project.momeasy.domain.calendar.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum CalendarErrorCode implements BaseErrorCode {

    // 400 Bad Request
    INVALID_UPDATE_REQUEST(HttpStatus.BAD_REQUEST, "CALENDAR400_1", "요청한 일정 정보가 유효하지 않아 수정할 수 없습니다."),
    INVALID_DELETE_REQUEST(HttpStatus.BAD_REQUEST, "CALENDAR400_2", "요청한 일정 정보를 삭제할 수 없습니다."),

    // 401 Unauthorized
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "CALENDAR401", "일정에 접근할 권한이 없습니다."),

    // 404 Not Found
    NOT_FOUND(HttpStatus.NOT_FOUND, "CALENDAR404", "해당 일정을 찾을 수 없습니다."),

    // 409 Conflict
    DUPLICATE_SCHEDULE(HttpStatus.CONFLICT, "CALENDAR409", "해당 날짜에 이미 일정이 존재합니다."),

    // 500 Internal Server Error
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "CALENDAR500", "서버 내부 오류로 일정 처리에 실패했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
