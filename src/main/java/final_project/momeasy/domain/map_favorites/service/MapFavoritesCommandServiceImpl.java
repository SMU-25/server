package final_project.momeasy.domain.map_favorites.service;

import final_project.momeasy.domain.map_favorites.converter.MapFavoritesConverter;
import final_project.momeasy.domain.map_favorites.dto.MapFavoritesRequestDTO;
import final_project.momeasy.domain.map_favorites.dto.MapFavoritesResponseDTO;
import final_project.momeasy.domain.map_favorites.entity.MapFavorites;
import final_project.momeasy.domain.map_favorites.exception.MapFavoritesException;
import final_project.momeasy.domain.map_favorites.exception.MapFavortiesErrorCode;
import final_project.momeasy.domain.map_favorites.repository.MapFavoritesRepository;
import final_project.momeasy.domain.parent.entity.Parent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapFavoritesCommandServiceImpl implements MapFavoritesCommandService {

    private final MapFavoritesRepository mapFavoritesRepository;

    public MapFavoritesResponseDTO.MapFavoritesCreateDTO createMapFavorites(Parent parent, MapFavoritesRequestDTO.MapFavoritesCreateDTO mapFavoritesCreateDTO) {
        if(parent == null){
            throw new MapFavoritesException(MapFavortiesErrorCode.UNAUTHORIZED_ACCESS);
        }
        MapFavorites mapFavorites = MapFavoritesConverter.toMapFavorites(mapFavoritesCreateDTO);
        mapFavorites.setParent(parent);
        mapFavoritesRepository.save(mapFavorites);
        return MapFavoritesConverter.toMapFavoritesCreateDTO(mapFavorites);
    }

    public void deleteMapFavorites(Parent parent, Long mapId) {
        Long parentId = parent.getId();
        if(!mapFavoritesRepository.existsByMapIdAndParentId(mapId, parentId)){
            throw new MapFavoritesException(MapFavortiesErrorCode.UNAUTHORIZED_ACCESS);
        }
        MapFavorites mapFavorites = mapFavoritesRepository.findById(mapId).orElseThrow(() -> new MapFavoritesException(MapFavortiesErrorCode.NOT_FOUND));
        mapFavoritesRepository.delete(mapFavorites);
    }
}
