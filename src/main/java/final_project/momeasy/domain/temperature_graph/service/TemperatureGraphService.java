package final_project.momeasy.domain.temperature_graph.service;

import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.temperature_graph.dto.TemperatureGraphResponseDTO;
import final_project.momeasy.domain.temperature_graph.entity.TemperatureGraph;

import java.util.List;

public interface TemperatureGraphService {
    // service 안에서만 사용 -> entity 반환해도 괜찮음
    List<TemperatureGraph> createTemperatureRecordGraph(Long recordId, Parent parent, Long childId);
    List<TemperatureGraphResponseDTO.TemperatureGraphHomecamViewDTO> getTemperatureRecordHomecamGraph(Parent parent, Long childId);
}
