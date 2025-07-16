package final_project.momeasy.domain.parent.service.query;

import final_project.momeasy.domain.parent.dto.response.ParentResponseDTO;

public interface ParentQueryService {

    ParentResponseDTO.ParentDetailResponseDTO getParentDetail(Long parentId);
}
