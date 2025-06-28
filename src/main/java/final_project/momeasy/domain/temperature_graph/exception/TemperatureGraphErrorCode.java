package final_project.momeasy.domain.temperature_graph.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum TemperatureGraphErrorCode {
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "TEMPERATURE_GRAPH401_GRAPH401", "온도 그래프에 접근할 수 있는 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "TEMPERATURE_GRAPH401_GRAPH404", "온도 그래프을 찾을 수 없습니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "TEMPERATURE_GRAPH401_GRAPH500", "온도 그래프 처리 중 서버 오류가 발생했습니다");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
