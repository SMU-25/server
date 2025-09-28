package final_project.momeasy.domain.map_favorites.exception;

import final_project.momeasy.global.apiPayload.code.BaseErrorCode;
import final_project.momeasy.global.apiPayload.exception.CustomException;

public class MapFavoritesException extends CustomException {
    public MapFavoritesException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
