package final_project.momeasy.domain.fever_report.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum FeverReportErrorCode implements BaseErrorCode {
    DELETE_FAILED(HttpStatus.BAD_REQUEST, "FEVER_RECORD400_2", "발열 리포트 정보를 삭제할 수 없습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "FEVER_RECORD401", "발열 리포트에 접근할 수 있는 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "FEVER_RECORD404", "발열 리포트를 찾을 수 없습니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FEVER_RECORD500", "발열 리포트 처리 중 서버 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
