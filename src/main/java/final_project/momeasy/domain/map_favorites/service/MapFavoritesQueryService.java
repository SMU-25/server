package final_project.momeasy.domain.map_favorites.service;

import final_project.momeasy.domain.map_favorites.dto.MapFavoritesResponseDTO;
import final_project.momeasy.domain.parent.entity.Parent;

public interface MapFavoritesQueryService {
    MapFavoritesResponseDTO.MapFavoritesDetailDTO getMapFavoritesById(Parent parent, Long mapId);
    MapFavoritesResponseDTO.MapFavoritesListViewDTO getMapFavoritesList(Parent parent, Long cursor, Integer size);
}
