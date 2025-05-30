package final_project.momeasy.domain.parent.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ParentErrorCode implements BaseErrorCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "PARENT400", "필수 동의사항에 모두 동의해야 회원가입이 가능합니다."),
    OAUTH_TOKEN_FAIL(HttpStatus.BAD_REQUEST, "PARENT400_1", "토큰 변환 실패"),
    OAUTH_USER_INFO_FAIL(HttpStatus.BAD_REQUEST, "PARENT400_2", "카카오 사용자 정보 조회 실패"),
    OAUTH_EMAIL_NOT_FOUND(HttpStatus.BAD_REQUEST, "PARENT400_3", "이메일 정보를 찾을 수 없습니다."),
    OAUTH_LOGIN_FAIL(HttpStatus.BAD_REQUEST, "PARENT400_4", "로그인에 실패했습니다."),
    LOGIN_ID_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "PARENT400_5", "해당 LoginID가 이미 존재합니다."),
    WRONG_PASSWORD(HttpStatus.UNAUTHORIZED, "PARENT401_1", "비밀번호가 일치하지 않습니다."),
    WRONG_ID(HttpStatus.UNAUTHORIZED, "PARENT401_2", "아이디가 일치하지 않습니다."),
    WRONG_CODE(HttpStatus.UNAUTHORIZED, "PARENT401_3", "인증번호가 일치하지 않습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "PARENT401_4", "회원 정보를 수정/삭제할 권한이 없습니다."),
    SOCIAL_USER_CANNOT_LOGIN(HttpStatus.UNAUTHORIZED, "PARENT401_5", "소셜 로그인 사용자는 로컬 로그인할 수 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "PARENT404", "회원을 찾을 수 없습니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "PARENT409_1", "이미 사용 중인 이메일입니다."),
    DUPLICATE_ID(HttpStatus.CONFLICT, "PARENT409_2", "이미 사용중인 아이디입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
