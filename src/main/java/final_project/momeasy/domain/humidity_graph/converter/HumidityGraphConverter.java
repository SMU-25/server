package final_project.momeasy.domain.humidity_graph.converter;

import final_project.momeasy.domain.humidity_graph.dto.HumidityGraphResponseDTO;
import final_project.momeasy.domain.humidity_graph.entity.HumidityGraph;

public class HumidityGraphConverter {
    public static HumidityGraphResponseDTO.HumidityGraphCreateDTO toHumidityGraphCreateDTO(HumidityGraph humidityGraph) {
        return HumidityGraphResponseDTO.HumidityGraphCreateDTO.builder()
                .avghumidity(humidityGraph.getAvgHumidity())
                .build();
    }

    public static HumidityGraphResponseDTO.HumidityGraphViewDTO toHumidityGraphViewDTO(HumidityGraph humidityGraph) {
        return HumidityGraphResponseDTO.HumidityGraphViewDTO.builder()
                .avghumidity(humidityGraph.getAvgHumidity())
                .build();
    }
}
