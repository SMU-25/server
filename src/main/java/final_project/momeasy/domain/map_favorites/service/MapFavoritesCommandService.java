package final_project.momeasy.domain.map_favorites.service;

import final_project.momeasy.domain.map_favorites.dto.MapFavoritesRequestDTO;
import final_project.momeasy.domain.map_favorites.dto.MapFavoritesResponseDTO;
import final_project.momeasy.domain.parent.entity.Parent;

public interface MapFavoritesCommandService {
    MapFavoritesResponseDTO.MapFavoritesCreateDTO createMapFavorites(Parent parent, MapFavoritesRequestDTO.MapFavoritesCreateDTO mapFavoritesCreateDTO);
    void deleteMapFavorites(Parent parent, Long mapId);
}
