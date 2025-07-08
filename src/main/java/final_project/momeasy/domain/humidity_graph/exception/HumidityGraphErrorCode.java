package final_project.momeasy.domain.humidity_graph.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum HumidityGraphErrorCode {
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "HUMIDITY_GRAPH401", "습도 그래프에 접근할 수 있는 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "HUMIDITY_GRAPH404", "습도 그래프을 찾을 수 없습니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "HUMIDITY_GRAPH500", "습도 그래프 처리 중 서버 오류가 발생했습니다");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
