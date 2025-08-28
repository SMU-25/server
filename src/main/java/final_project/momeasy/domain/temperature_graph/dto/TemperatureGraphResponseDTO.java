package final_project.momeasy.domain.temperature_graph.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import final_project.momeasy.common.enums.DayRange;
import lombok.Builder;
import lombok.Getter;

public class TemperatureGraphResponseDTO {
    @Getter
    @Builder
    public static class TemperatureGraphCreateDTO{
        private float avgtemperature;
    }

    @Getter
    @Builder
    public static class TemperatureGraphViewDTO{
        private float avgtemperature;
    }

    @Getter
    @Builder
    public static class TemperatureGraphHomecamViewDTO{
        private float avgtemperature;
        @JsonIgnore
        private DayRange dayRange;
    }
}
