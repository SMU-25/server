package final_project.momeasy.global.auth.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {
    WRONG_CREDENTIALS(HttpStatus.UNAUTHORIZED, "AUTH401", "아이디 또는 비밀번호가 일치하지 않습니다."),
    WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "AUTH401_1", "비밀번호가 일치하지 않습니다."),
    WRONG_ID(HttpStatus.UNAUTHORIZED, "AUTH401_2", "아이디가 일치하지 않습니다."),
    WRONG_CODE(HttpStatus.UNAUTHORIZED, "AUTH401_3", "인증번호가 일치하지 않습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "AUTH401_4", "접근 권한이 없습니다."),
    SOCIAL_USER_CANNOT_LOGIN(HttpStatus.UNAUTHORIZED, "AUTH401_5", "소셜 로그인 사용자는 로컬 로그인할 수 없습니다."),
    SOCIAL_USER_CANNOT_UPDATE_PASSWORD(HttpStatus.UNAUTHORIZED, "AUTH401_6", "소셜 로그인 사용자는 비밀번호를 변경할 수 없습니다."),
    OAUTH_TOKEN_FAIL(HttpStatus.BAD_REQUEST, "AUTH400_1", "소셜 로그인 토큰 발급 중 오류가 발생했습니다."),
    OAUTH_USER_INFO_FAIL(HttpStatus.BAD_REQUEST, "AUTH400_2", "소셜 사용자 정보 조회에 실패했습니다."),
    OAUTH_EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "AUTH400_3", "이메일 정보를 찾을 수 없습니다."),
    OAUTH_LOGIN_FAIL(HttpStatus.BAD_REQUEST, "AUTH400_4", "로그인에 실패했습니다."),
    ACCOUNT_DISABLED(HttpStatus.FORBIDDEN, "AUTH403", "계정이 비활성화 되었습니다."),
    ACCOUNT_LOCKED(HttpStatus.LOCKED, "AUTH423", "계정이 잠금 상태입니다."),
    LOGIN_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "AUTH500", "인증에 실패했습니다.");


    private final HttpStatus status;
    private final String code;
    private final String message;
}
