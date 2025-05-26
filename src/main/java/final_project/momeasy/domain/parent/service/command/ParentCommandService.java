package final_project.momeasy.domain.parent.service.command;

import final_project.momeasy.domain.parent.dto.request.ParentRequestDTO;
import final_project.momeasy.domain.parent.dto.response.ParentResponseDTO;

public interface ParentCommandService {

    ParentResponseDTO.ParentCreateResponseDTO createParent(ParentRequestDTO.ParentCreateRequestDTO parentCreateRequestDTO);
}
