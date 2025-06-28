package final_project.momeasy.domain.fever_graph.converter;

import final_project.momeasy.domain.fever_graph.dto.FeverGraphResponseDTO;
import final_project.momeasy.domain.fever_graph.entity.FeverGraph;

public class FeverGraphConverter {
    public static FeverGraphResponseDTO.FeverGraphCreateDTO toFeverGraphCreateDTO(FeverGraph feverGraph) {
        return FeverGraphResponseDTO.FeverGraphCreateDTO.builder()
                .fever(feverGraph.getFever())
                .build();
    }

    public static FeverGraphResponseDTO.FeverGraphViewDTO toFeverGraphViewDTO(FeverGraph feverGraph) {
        return FeverGraphResponseDTO.FeverGraphViewDTO.builder()
                .fever(feverGraph.getFever())
                .build();
    }
}
