package final_project.momeasy.domain.home_cam.service;

import final_project.momeasy.domain.home_cam.converter.HomecamConverter;
import final_project.momeasy.domain.home_cam.dto.HomecamRequestDTO;
import final_project.momeasy.domain.home_cam.dto.HomecamResponseDTO;
import final_project.momeasy.domain.home_cam.entity.Homecam;
import final_project.momeasy.domain.home_cam.exception.HomecamErrorCode;
import final_project.momeasy.domain.home_cam.exception.HomecamException;
import final_project.momeasy.domain.parent.entity.Parent;

public interface HomecamService {
    public void deleteHomecam(Long homecamId, Parent parent);

    public HomecamResponseDTO.HomecamDTO createHomecam(HomecamRequestDTO.HomecamRegisterDTO homecamRequestDTO, Parent parent);
}
