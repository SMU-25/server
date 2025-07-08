package final_project.momeasy.domain.room_condition.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class RoomConditionResponseDTO {
    @Getter
    @Builder
    public static class RoomConditionViewDTO {
        private float temperature;
        private float humidity;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    public static class RoomConditionListViewDTO {
        private List<RoomConditionViewDTO> roomConditions;
        private Boolean hasNext;
        private Long cursor;
    }

    @Getter
    @Builder
    public static class RoomConditionGrpahDTO {
        private float avgtemperature;
        private float avghumidity;
    }
}
