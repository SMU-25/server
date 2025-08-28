package final_project.momeasy.domain.humidity_graph.service;

import final_project.momeasy.domain.humidity_graph.dto.HumidityGraphResponseDTO;
import final_project.momeasy.domain.humidity_graph.entity.HumidityGraph;
import final_project.momeasy.domain.parent.entity.Parent;

import java.util.List;

public interface HumidityGraphService {
    // service 안에서만 사용 -> entity 반환해도 괜찮음
    List<HumidityGraph> createHumidityRecordGraph(Long recordId, Parent parent, Long childId);
    List<HumidityGraphResponseDTO.HumidityGraphHomecamViewDTO> getHumidityRecordHomecamGraph(Parent parent,  Long childId);
}
