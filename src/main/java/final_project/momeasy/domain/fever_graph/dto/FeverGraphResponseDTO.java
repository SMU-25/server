package final_project.momeasy.domain.fever_graph.dto;

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
}
