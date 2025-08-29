package final_project.momeasy.domain.announcement.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum AnnouncementErrorCode implements BaseErrorCode {

    // 400 Bad Request
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "ANNOUNCEMENT400_1", "요청한 공지/이벤트 정보가 유효하지 않습니다."),

    // 401 Unauthorized
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "ANNOUNCEMENT401_1", "공지/이벤트에 접근할 권한이 없습니다."),

    // 404 Not Found
    NOT_FOUND(HttpStatus.NOT_FOUND, "ANNOUNCEMENT404_1", "해당 공지/이벤트를 찾을 수 없습니다."),
    SEARCH_NO_RESULT(HttpStatus.NOT_FOUND, "ANNOUNCEMENT404_2", "검색 결과가 존재하지 않습니다."),

    // 500 Internal Server Error
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "ANNOUNCEMENT500_1", "서버 내부 오류로 공지/이벤트 처리에 실패했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
