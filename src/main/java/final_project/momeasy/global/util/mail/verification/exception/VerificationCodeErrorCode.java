package final_project.momeasy.global.util.mail.verification.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum VerificationCodeErrorCode implements BaseErrorCode {
    CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "VERI404_1", "인증 코드가 존재하지 않습니다."),
    CODE_EXPIRED(HttpStatus.UNAUTHORIZED, "VERI401_1", "인증 코드가 만료되었습니다."),
    CODE_MISMATCH(HttpStatus.BAD_REQUEST, "VERI400_1", "인증 코드가 일치하지 않습니다."),
    CODE_ALREADY_VERIFIED(HttpStatus.BAD_REQUEST, "VERI400_2", "이미 인증이 완료된 이메일입니다."),
    CODE_NOT_VERIFIED(HttpStatus.FORBIDDEN, "VERI403_1", "이메일 인증이 완료되지 않았습니다."),
    CODE_GENERATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "VERI500_1", "인증 코드 생성에 실패했습니다."),
    REDIS_SAVE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "VERI500_2", "인증 코드를 저장하는 데 실패했습니다."),
    REDIS_READ_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "VERI500_3", "저장된 인증 정보를 불러오는 데 실패했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
