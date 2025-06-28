package final_project.momeasy.domain.temperature_graph.converter;


import final_project.momeasy.domain.temperature_graph.dto.TemperatureGraphResponseDTO;
import final_project.momeasy.domain.temperature_graph.entity.TemperatureGraph;

public class TemperatureGraphConverter {
    public static TemperatureGraphResponseDTO.TemperatureGraphCreateDTO toTemperatureGraphCreateDTO(TemperatureGraph temperatureGraph) {
        return TemperatureGraphResponseDTO.TemperatureGraphCreateDTO.builder()
                .temperature(temperatureGraph.getTemperature())
                .build();
    }

    public static TemperatureGraphResponseDTO.TemperatureGraphViewDTO toTemperatureGraphViewDTO(TemperatureGraph temperatureGraph) {
        return TemperatureGraphResponseDTO.TemperatureGraphViewDTO.builder()
                .temperature(temperatureGraph.getTemperature())
                .build();
    }
}
