package final_project.momeasy.domain.map_favorites.dto;

import final_project.momeasy.common.enums.MapType;
import lombok.Builder;
import lombok.Getter;

public class MapFavoritesRequestDTO {
    @Getter
    @Builder
    public static class MapFavoritesCreateDTO {
        private String place_name;
        private MapType category_group_code;
        private String category_name;
        private String category_group_name;
        private String phone;
        private String address_name;
        private String road_address_name;
        private String place_url;
    }
}
