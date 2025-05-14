package final_project.momeasy.domain.setting.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum SettingErrorCode implements BaseErrorCode {
    UPDATE_FAILED(HttpStatus.BAD_REQUEST, "SETTING400_1", "설정 정보를 수정할 수 없습니다"),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "SETTING401", "설정에 접근할 수 있는 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "SETTING404", "설정를 찾을 수 없습니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SETTING500", "설정 처리 중 서버 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
