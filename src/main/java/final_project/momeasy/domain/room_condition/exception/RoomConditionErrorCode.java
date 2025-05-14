package final_project.momeasy.domain.room_condition.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum RoomConditionErrorCode implements BaseErrorCode {
    DELETE_FAILED(HttpStatus.BAD_REQUEST, "ROOM_CONDITION400_2", "온습도 기록 정보를 삭제할 수 없습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "ROOM_CONDITION401", "온습도 기록에 접근할 수 있는 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "ROOM_CONDITION404", "온습도 기록을 찾을 수 없습니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "ROOM_CONDITION500", "온습도 기록 처리 중 서버 오류가 발생했습니다");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
