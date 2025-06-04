package final_project.momeasy.domain.child.service.command;

import final_project.momeasy.domain.child.dto.request.ChildRequestDTO;
import final_project.momeasy.domain.child.dto.response.ChildResponseDTO;

public interface ChildCommandService {

    ChildResponseDTO.ChildCreateResponseDTO createChild(ChildRequestDTO.ChildCreateRequestDTO dto, Long parentId);
}
