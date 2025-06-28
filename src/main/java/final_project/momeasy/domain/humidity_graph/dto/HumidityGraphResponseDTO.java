package final_project.momeasy.domain.humidity_graph.dto;

import lombok.Builder;
import lombok.Getter;

public class HumidityGraphResponseDTO {
    @Getter
    @Builder
    public static class HumidityGraphCreateDTO{
        private float humidity;
    }

    @Getter
    @Builder
    public static class HumidityGraphViewDTO{
        private float humidity;
    }
}
