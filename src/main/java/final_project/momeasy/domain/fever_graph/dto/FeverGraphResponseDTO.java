package final_project.momeasy.domain.fever_graph.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import final_project.momeasy.common.enums.DayRange;
import lombok.Builder;
import lombok.Getter;

public class FeverGraphResponseDTO {
    @Getter
    @Builder
    public static class FeverGraphCreateDTO{
        private float avgfever;
    }

    @Getter
    @Builder
    public static class FeverGraphViewDTO{
        private float avgfever;
    }

    @Getter
    @Builder
    public static class FeverGraphHomecamViewDTO{
        private float avgfever;
        @JsonIgnore
        private DayRange dayRange;
    }
}
