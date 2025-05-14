package final_project.momeasy.domain.calendar.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum CalendarErrorCode implements BaseErrorCode {
    UPDATE_FAILED(HttpStatus.BAD_REQUEST, "CALENDAR400_1", "캘린더 정보를 수정할 수 없습니다"),
    DELETE_FAILED(HttpStatus.BAD_REQUEST, "CALENDAR400_2", "캘린더 정보를 삭제할 수 없습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "CALENDAR401", "캘린더에 접근할 수 있는 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "CALENDAR404", "캘린더를 찾을 수 없습니다."),
    ALREADY_ADD(HttpStatus.CONFLICT, "CALENDAR409", "이미 등록된 캘린더입니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "CALENDAR500", "캘린더 처리 중 서버 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
