package final_project.momeasy.domain.map_favorites.controller;

import final_project.momeasy.domain.map_favorites.dto.MapFavoritesRequestDTO;
import final_project.momeasy.domain.map_favorites.dto.MapFavoritesResponseDTO;
import final_project.momeasy.domain.map_favorites.service.MapFavoritesCommandService;
import final_project.momeasy.domain.map_favorites.service.MapFavoritesQueryService;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.security.annotation.AuthParent;
import final_project.momeasy.global.validation.annoation.CheckCursor;
import final_project.momeasy.global.validation.annoation.CheckSize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mapFavorites")
@Tag(name = "MapFavorites", description = "지도 즐겨찾기 API by 김준형")
public class MapFavoritesController {

    private final MapFavoritesCommandService mapFavoritesCommandService;
    private final MapFavoritesQueryService mapFavoritesQueryService;

    @GetMapping("/{mapId}")
    @Operation(summary = "즐겨찾기 상세 조회", description = "지도에 등록된 특정 즐겨찾기의 상세 정보를 조회합니다. 지도 즐겨찾기 화면에서 사용되는 API입니다.")
    public CustomResponse<MapFavoritesResponseDTO.MapFavoritesDetailDTO> getMapFavorites(
            @AuthParent Parent parent,
            @PathVariable("mapId") Long mapId) {
        return CustomResponse.onSuccess(mapFavoritesQueryService.getMapFavoritesById(parent,mapId));
    }

    @GetMapping("/list")
    @Operation(summary = "즐겨찾기 목록 조회", description = "지도에 등록된 모든 즐겨찾기를 조회합니다. 지도 즐겨찾기 화면에서 사용되는 API입니다.")
    @Parameter(name = "cursor", description = "데이터가 시작하는 부분을 표시합니다. 0부터 시작합니다.", example = "0")
    @Parameter(name = "size", description = "size만큼 데이터를 가져옵니다.", example = "10")
    public CustomResponse<MapFavoritesResponseDTO.MapFavoritesListViewDTO> getMapFavorites(
            @AuthParent Parent parent,
            @RequestParam(name = "cursor") @CheckCursor Long cursor,
            @RequestParam(name = "size") @CheckSize Integer size) {
        return CustomResponse.onSuccess(mapFavoritesQueryService.getMapFavoritesList(parent,cursor,size));
    }

    @PostMapping
    @Operation(summary = "즐겨찾기 생성", description = "지도에 새로운 즐겨찾기를 생성합니다. 지도 즐겨찾기 화면에서 사용되는 API입니다.")
    public CustomResponse<MapFavoritesResponseDTO.MapFavoritesCreateDTO> getMapFavorites(
            @AuthParent Parent parent,
            @RequestBody MapFavoritesRequestDTO.MapFavoritesCreateDTO mapFavoritesCreateDTO
    ) {
        return CustomResponse.onSuccess(HttpStatus.CREATED,mapFavoritesCommandService.createMapFavorites(parent,mapFavoritesCreateDTO));
    }

    @DeleteMapping("/{mapId}")
    @Operation(summary = "즐겨찾기 삭제", description = "지도에 등록된 즐겨찾기를 삭제합니다. 지도 즐겨찾기 화면에서 사용되는 API입니다.")
    public CustomResponse<String> deleteMapFavorites(
            @AuthParent Parent parent,
            @PathVariable("mapId") Long mapId
    ){
        mapFavoritesCommandService.deleteMapFavorites(parent,mapId);
        return CustomResponse.onSuccess(HttpStatus.NO_CONTENT,"지도 즐겨찾기 삭제 완료");
    }

}
