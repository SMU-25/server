package final_project.momeasy.domain.humidity_graph.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import final_project.momeasy.common.enums.DayRange;
import lombok.Builder;
import lombok.Getter;

public class HumidityGraphResponseDTO {
    @Getter
    @Builder
    public static class HumidityGraphCreateDTO{
        private float avghumidity;
    }

    @Getter
    @Builder
    public static class HumidityGraphViewDTO{
        private float avghumidity;
    }

    @Getter
    @Builder
    public static class HumidityGraphHomecamViewDTO{
        private float avghumidity;

        @JsonIgnore
        private DayRange dayRange;
    }

}
