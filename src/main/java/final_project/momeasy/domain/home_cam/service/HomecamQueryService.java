package final_project.momeasy.domain.home_cam.service;

import final_project.momeasy.domain.home_cam.dto.HomecamResponseDTO;
import final_project.momeasy.domain.parent.entity.Parent;

import java.util.List;

public interface HomecamQueryService {
    HomecamResponseDTO.HomecamDetailDTO getHomecamById(Long homecamId, Parent parent);

    List<HomecamResponseDTO.HomecamDTO> getHomecamListByParent(Parent parent);
}
