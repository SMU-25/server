package final_project.momeasy.domain.fever_graph.service;

import final_project.momeasy.domain.fever_graph.entity.FeverGraph;
import final_project.momeasy.domain.parent.entity.Parent;

import java.util.List;

public interface FeverGraphService {
    // service 안에서만 사용 -> entity 반환해도 괜찮음
    List<FeverGraph> createFeverRecordGraph(Long recordId, Parent parent, Long childId);
}
