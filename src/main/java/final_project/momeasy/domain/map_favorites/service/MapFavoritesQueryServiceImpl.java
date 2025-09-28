package final_project.momeasy.domain.map_favorites.service;

import final_project.momeasy.domain.map_favorites.converter.MapFavoritesConverter;
import final_project.momeasy.domain.map_favorites.dto.MapFavoritesResponseDTO;
import final_project.momeasy.domain.map_favorites.entity.MapFavorites;
import final_project.momeasy.domain.map_favorites.exception.MapFavoritesException;
import final_project.momeasy.domain.map_favorites.exception.MapFavortiesErrorCode;
import final_project.momeasy.domain.map_favorites.repository.MapFavoritesRepository;
import final_project.momeasy.domain.parent.entity.Parent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MapFavoritesQueryServiceImpl implements MapFavoritesQueryService {

    private final MapFavoritesRepository mapFavoritesRepository;

    @Override
    public MapFavoritesResponseDTO.MapFavoritesDetailDTO getMapFavoritesById(Parent parent, Long mapId) {
        MapFavorites mapFavorites = mapFavoritesRepository.findById(mapId).orElseThrow(
                ()->new MapFavoritesException(MapFavortiesErrorCode.NOT_FOUND));
        if(!mapFavoritesRepository.existsByMapIdAndParentId(mapId, parent.getId())) {
            log.warn("지도 즐겨찾기 조회 권한 없음");
            throw new MapFavoritesException(MapFavortiesErrorCode.UNAUTHORIZED_ACCESS);
        }
        log.info("지도 즐겨찾기 조회 성공");
        return MapFavoritesConverter.toMapFavoritesDetailDTO(mapFavorites);
    }

    public MapFavoritesResponseDTO.MapFavoritesListViewDTO getMapFavoritesList(Parent parent, Long cursor, Integer size) {
        if(parent==null) {
            log.warn("지도 즐겨찾기 조회 권한 없음");
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
            log.warn("지도 즐겨찾기 데이터 없음");
            throw new MapFavoritesException(MapFavortiesErrorCode.NOT_FOUND);
        }

        Long nextCursor = null;
        if(!mapFavoritesList.isEmpty() && mapFavoritesSlice.hasNext()){
            nextCursor = mapFavoritesList.get(mapFavoritesList.size()-1).getId();
        }
        log.info("지도 즐겨찾기 목록 조회 성공");
        return MapFavoritesConverter.toMapFavoritesListViewDTO(mapFavoritesListViewDTO,nextCursor,mapFavoritesSlice.hasNext());

    }
}
