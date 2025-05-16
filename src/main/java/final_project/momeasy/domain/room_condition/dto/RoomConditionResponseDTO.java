package final_project.momeasy.domain.room_condition.dto;

import lombok.Builder;
import lombok.Getter;

public class RoomConditionResponseDTO {
    @Getter
    @Builder
    public static class RoomConditionViewDTO {
        private float temperature;
        private float humidity;
    }
}
