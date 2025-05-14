package final_project.momeasy.domain.home_cam.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum HomecamErrorCode implements BaseErrorCode {
    UPDATE_FAILED(HttpStatus.BAD_REQUEST, "HOMECAM400_1", "홈캠 정보를 수정할 수 없습니다"),
    DELETE_FAILED(HttpStatus.BAD_REQUEST, "HOMECAM400_2", "홈캠 정보를 삭제할 수 없습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "HOMECAM401", "홈캠에 접근할 수 있는 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "HOMECAM404", "홈캠을 찾을 수 없습니다."),
    ALREADY_ADD(HttpStatus.CONFLICT, "HOMECAM409", "이미 등록된 홈캠입니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "HOMECAM500", "홈캠 처리 중 서버 오류가 발생했습니다");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
