package final_project.momeasy.domain.room_condition.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;

public class RoomConditionRequestDTO {
    @Getter
    @Builder
    public static class RoomConditionCreateDTO {
        private float temperature;
        private float humidity;
    }
}
