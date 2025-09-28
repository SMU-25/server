package final_project.momeasy.domain.map_favorites.service;

import final_project.momeasy.domain.map_favorites.converter.MapFavoritesConverter;
import final_project.momeasy.domain.map_favorites.dto.MapFavoritesResponseDTO;
import final_project.momeasy.domain.map_favorites.entity.MapFavorites;
import final_project.momeasy.domain.map_favorites.exception.MapFavoritesException;
import final_project.momeasy.domain.map_favorites.exception.MapFavortiesErrorCode;
import final_project.momeasy.domain.map_favorites.repository.MapFavoritesRepository;
import final_project.momeasy.domain.parent.entity.Parent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MapFavoritesQueryServiceImpl implements MapFavoritesQueryService {

    private final MapFavoritesRepository mapFavoritesRepository;

    @Override
    public MapFavoritesResponseDTO.MapFavoritesDetailDTO getMapFavoritesById(Parent parent, Long mapId) {
        MapFavorites mapFavorites = mapFavoritesRepository.findById(mapId).orElseThrow(
                ()->new MapFavoritesException(MapFavortiesErrorCode.NOT_FOUND));
        if(!mapFavoritesRepository.existsByMapIdAndParentId(mapId, parent.getId())) {
            throw new MapFavoritesException(MapFavortiesErrorCode.UNAUTHORIZED_ACCESS);
        }
        return MapFavoritesConverter.toMapFavoritesDetailDTO(mapFavorites);
    }

    public MapFavoritesResponseDTO.MapFavoritesListViewDTO getMapFavoritesList(Parent parent, Long cursor, Integer size) {
        if(parent==null) {
            throw new MapFavoritesException(MapFavortiesErrorCode.UNAUTHORIZED_ACCESS);
        }
        Long parentId = parent.getId();
        if(cursor == 0) {
            cursor = Long.MAX_VALUE;
        }
        Pageable pageable = PageRequest.of(0, size);
        Slice<MapFavorites> mapFavoritesSlice = mapFavoritesRepository.findMapFavoritesCursorPagination(parentId,cursor,pageable);
        List<MapFavorites> mapFavoritesList = mapFavoritesSlice.toList();
        List<MapFavoritesResponseDTO.MapFavoritesViewDTO> mapFavoritesListViewDTO =
                mapFavoritesList.stream().map(MapFavoritesConverter::toMapFavoritesViewDTO).toList();

        if(mapFavoritesSlice.isEmpty()){
            throw new MapFavoritesException(MapFavortiesErrorCode.NOT_FOUND);
        }

        Long nextCursor = null;
        if(!mapFavoritesList.isEmpty() && mapFavoritesSlice.hasNext()){
            nextCursor = mapFavoritesList.get(mapFavoritesList.size()-1).getId();
        }
        return MapFavoritesConverter.toMapFavoritesListViewDTO(mapFavoritesListViewDTO,nextCursor,mapFavoritesSlice.hasNext());

    }
}
