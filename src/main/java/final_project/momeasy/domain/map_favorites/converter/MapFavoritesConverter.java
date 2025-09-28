package final_project.momeasy.domain.map_favorites.converter;

import final_project.momeasy.domain.map_favorites.dto.MapFavoritesRequestDTO;
import final_project.momeasy.domain.map_favorites.dto.MapFavoritesResponseDTO;
import final_project.momeasy.domain.map_favorites.entity.MapFavorites;

import java.util.List;

public class MapFavoritesConverter {
    public static MapFavoritesResponseDTO.MapFavoritesDetailDTO toMapFavoritesDetailDTO(MapFavorites mapFavorites) {
        return MapFavoritesResponseDTO.MapFavoritesDetailDTO.builder()
                .phone(mapFavorites.getPhone())
                .address_name(mapFavorites.getAddress_name())
                .category_group_name(mapFavorites.getCategory_group_name())
                .place_name(mapFavorites.getPlace_name())
                .place_url(mapFavorites.getPlace_url())
                .road_address_name(mapFavorites.getRoad_address_name())
                .build();
    }

    public static MapFavoritesResponseDTO.MapFavoritesViewDTO toMapFavoritesViewDTO(MapFavorites mapFavorites) {
        return MapFavoritesResponseDTO.MapFavoritesViewDTO.builder()
                .map_favorites_id(mapFavorites.getId())
                .address_name(mapFavorites.getAddress_name())
                .place_name(mapFavorites.getPlace_name())
                .road_address_name(mapFavorites.getRoad_address_name())
                .build();
    }

    public static MapFavoritesResponseDTO.MapFavoritesListViewDTO toMapFavoritesListViewDTO(List<MapFavoritesResponseDTO.MapFavoritesViewDTO> mapFavorites, Long cursor, Boolean hasNext) {
        return MapFavoritesResponseDTO.MapFavoritesListViewDTO.builder()
                .cursor(cursor)
                .hasNext(hasNext)
                .favorites(mapFavorites)
                .build();
    }

    public static MapFavorites toMapFavorites(MapFavoritesRequestDTO.MapFavoritesCreateDTO mapFavoritesRequestDTO) {
        return MapFavorites.builder()
                .place_name(mapFavoritesRequestDTO.getPlace_name())
                .category_group_code(mapFavoritesRequestDTO.getCategory_group_code())
                .category_name(mapFavoritesRequestDTO.getCategory_name())
                .category_group_name(mapFavoritesRequestDTO.getCategory_group_name())
                .phone(mapFavoritesRequestDTO.getPhone())
                .address_name(mapFavoritesRequestDTO.getAddress_name())
                .road_address_name(mapFavoritesRequestDTO.getRoad_address_name())
                .place_url(mapFavoritesRequestDTO.getPlace_url())
                .build();
    }

    public static MapFavoritesResponseDTO.MapFavoritesCreateDTO toMapFavoritesCreateDTO(MapFavorites mapFavorites) {
        return MapFavoritesResponseDTO.MapFavoritesCreateDTO.builder()
                .place_name(mapFavorites.getPlace_name())
                .category_group_name(mapFavorites.getCategory_group_name())
                .phone(mapFavorites.getPhone())
                .address_name(mapFavorites.getAddress_name())
                .road_address_name(mapFavorites.getRoad_address_name())
                .place_url(mapFavorites.getPlace_url())
                .build();
    }
}
