package final_project.momeasy.domain.map_favorites.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum MapFavortiesErrorCode implements BaseErrorCode {
    UPDATE_FAILED(HttpStatus.BAD_REQUEST, "MAPFAVORTIES400_1", "지도 즐겨찾기 정보를 수정할 수 없습니다"),
    DELETE_FAILED(HttpStatus.BAD_REQUEST, "MAPFAVORTIES400_2", "지도 즐겨찾기 정보를 삭제할 수 없습니다."),
    UNAUTHORIZED_ACCESS(HttpStatus.UNAUTHORIZED, "MAPFAVORTIES401", "지도 즐겨찾기에 접근할 수 있는 권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "MAPFAVORTIES404", "지도 즐겨찾기을 찾을 수 없습니다."),
    ALREADY_ADD(HttpStatus.CONFLICT, "MAPFAVORTIES409", "이미 등록된 지도 즐겨찾기입니다."),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "MAPFAVORTIES500", "지도 즐겨찾기 처리 중 서버 오류가 발생했습니다");

    private final HttpStatus status;
    private final String code;
    private final String message;

}
