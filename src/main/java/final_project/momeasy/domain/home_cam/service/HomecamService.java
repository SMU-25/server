package final_project.momeasy.domain.home_cam.service;

import final_project.momeasy.domain.home_cam.dto.HomecamRequestDTO;
import final_project.momeasy.domain.home_cam.dto.HomecamResponseDTO;
import final_project.momeasy.domain.parent.entity.Parent;

public interface HomecamService {
    void deleteHomecam(Long homecamId, Parent parent);

    HomecamResponseDTO.HomecamDTO createHomecam(HomecamRequestDTO.HomecamCreateDTO homecamRequestDTO, Parent parent, Long childId);

    void updateHomecam(HomecamRequestDTO.HomecamUpdateDTO homecamUpdateDTO, Parent parent, Long childId, Long homecamId);

}
