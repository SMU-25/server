package final_project.momeasy.domain.room_condition.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

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
    public static class RoomConditionGrpahDTO {
        private float avgtemperature;
        private float avghumidity;
    }
}
