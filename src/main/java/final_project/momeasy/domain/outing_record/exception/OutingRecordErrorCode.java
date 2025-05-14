package final_project.momeasy.domain.outing_record.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum OutingRecordErrorCode implements BaseErrorCode {
    DELETE_FAILED(HttpStatus.BAD_REQUEST, "OUTING_RECORD400_2", "외출 기록 정보를 삭제할 수 없습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "OUTING_RECORD401", "외출 기록에 접근할 수 있는 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "OUTING_RECORD404", "외출 기록을 찾을 수 없습니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "OUTING_RECORD500", "외출 기록 처리 중 서버 오류가 발생했습니다");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
