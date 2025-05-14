package final_project.momeasy.domain.home_cam.service;

import final_project.momeasy.domain.home_cam.converter.HomecamConverter;
import final_project.momeasy.domain.home_cam.dto.HomecamResponseDTO;
import final_project.momeasy.domain.home_cam.entity.Homecam;
import final_project.momeasy.domain.home_cam.exception.HomecamErrorCode;
import final_project.momeasy.domain.home_cam.exception.HomecamException;
import final_project.momeasy.domain.parent.entity.Parent;

import java.util.List;

public interface HomecamQueryService {
    HomecamResponseDTO.HomecamDTO getHomecamById(Long homecamId, Parent parent);

    HomecamResponseDTO.HomecamVideoDTO getHomecamVideoById(Long homecamId, Parent parent);

    List<HomecamResponseDTO.HomecamDTO> getHomecamListByParent(Parent parent);
}
