package final_project.momeasy.domain.map_favorites.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class MapFavoritesResponseDTO {
    @Getter
    @Builder
    public static class MapFavoritesDetailDTO{
        private String place_name;
        private String category_group_name;
        private String address_name;
        private String road_address_name;
        private String phone;
        private String place_url;
    }

    @Getter
    @Builder
    public static class MapFavoritesCreateDTO{
        private String place_name;
        private String category_group_name;
        private String address_name;
        private String road_address_name;
        private String phone;
        private String place_url;
    }

    @Getter
    @Builder
    public static class MapFavoritesViewDTO{
        private Long map_favorites_id;
        private String place_name;
        private String address_name;
        private String road_address_name;
    }

    @Getter
    @Builder
    public static class MapFavoritesListViewDTO{
        private List<MapFavoritesViewDTO> favorites;
        private Boolean hasNext;
        private Long cursor;
    }
}