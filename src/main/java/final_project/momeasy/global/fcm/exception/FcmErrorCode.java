package final_project.momeasy.global.fcm.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum FcmErrorCode {
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "FCM 토큰을 찾을 수 없습니다."),
    TOKEN_NOT_OWNED(HttpStatus.FORBIDDEN, "본인 소유 토큰만 삭제할 수 있습니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    UNREGISTERED_TOKEN(HttpStatus.GONE, "더 이상 유효하지 않은 FCM 토큰입니다."),
    UNKNOWN_SENDER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FCM 전송 중 알 수 없는 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;

    FcmErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
