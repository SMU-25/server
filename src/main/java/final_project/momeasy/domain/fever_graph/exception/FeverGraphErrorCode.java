package final_project.momeasy.domain.fever_graph.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum FeverGraphErrorCode implements BaseErrorCode {
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "FEVER_GRAPH401", "체온 그래프에 접근할 수 있는 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "FEVER_GRAPH404", "체온 그래프을 찾을 수 없습니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FEVER_GRAPH500", "체온 그래프 처리 중 서버 오류가 발생했습니다");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
