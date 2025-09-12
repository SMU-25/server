package final_project.momeasy.domain.parent.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ParentErrorCode implements BaseErrorCode {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "PARENT400", "필수 동의사항에 모두 동의해야 회원가입이 가능합니다."),
    LOGIN_ID_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "PARENT400_1", "해당 LoginID가 이미 존재합니다."),
    IMAGE_NOT_PROVIDED(HttpStatus.BAD_REQUEST, "PARENT400_2", "프로필 이미지가 제공되지 않았습니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "PARENT409_1", "이미 사용 중인 이메일입니다."),
    DUPLICATE_ID(HttpStatus.CONFLICT, "PARENT409_2", "이미 사용 중인 아이디입니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "PARENT404", "회원을 찾을 수 없습니다."),
    SOFT_DELETED(HttpStatus.NOT_FOUND, "PARENT404_1", "삭제된 회원입니다."),;

    private final HttpStatus status;
    private final String code;
    private final String message;
}
