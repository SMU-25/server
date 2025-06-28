package final_project.momeasy.domain.temperature_graph.dto;

import lombok.Builder;
import lombok.Getter;

public class TemperatureGraphResponseDTO {
    @Getter
    @Builder
    public static class TemperatureGraphCreateDTO{
        private float temperature;
    }

    @Getter
    @Builder
    public static class TemperatureGraphViewDTO{
        private float temperature;
    }
}
