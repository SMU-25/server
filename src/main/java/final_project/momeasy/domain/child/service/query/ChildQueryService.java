package final_project.momeasy.domain.child.service.query;

import final_project.momeasy.domain.child.dto.response.ChildResponseDTO;
import final_project.momeasy.domain.parent.entity.Parent;

import java.util.List;

public interface ChildQueryService {

    ChildResponseDTO.ChildDetailResponseDTO getChild(Long childId, Parent parent);

    List<ChildResponseDTO.ChildSimpleResponseDTO> getChildren(Parent parent);
}
