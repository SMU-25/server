package final_project.momeasy.global.util.mail.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum MailErrorCode implements BaseErrorCode {
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "MAIL400_1", "이메일 형식이 올바르지 않습니다."),
    INVALID_RECIPIENT(HttpStatus.BAD_REQUEST, "MAIL400_2", "수신자 이메일이 존재하지 않거나 잘못되었습니다."),
    AUTHENTICATION_FAILED(HttpStatus.UNAUTHORIZED, "MAIL401_1", "메일 서버 인증에 실패했습니다."),
    CONNECTION_FAILED(HttpStatus.SERVICE_UNAVAILABLE, "MAIL503_1", "메일 서버에 연결할 수 없습니다."),
    EMAIL_SEND_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "MAIL500_1", "이메일 전송에 실패했습니다."),
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "MAIL500_2", "메일 처리 중 알 수 없는 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
