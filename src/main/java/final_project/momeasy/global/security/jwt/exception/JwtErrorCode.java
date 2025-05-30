package final_project.momeasy.global.security.jwt.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum JwtErrorCode implements BaseErrorCode {

    INVALID_SIGNATURE(HttpStatus.UNAUTHORIZED, "JWT401_1", "유효하지 않은 서명입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "JWT401_2", "토큰이 만료되었습니다."),
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, "JWT401_3", "잘못된 형식의 access token입니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "JWT401_4", "서명이 유효하지 않은 access token입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "JWT401_5", "지원하지 않는 토큰입니다."),
    MISSING_TOKEN(HttpStatus.UNAUTHORIZED, "JWT401_6", "토큰이 존재하지 않습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
