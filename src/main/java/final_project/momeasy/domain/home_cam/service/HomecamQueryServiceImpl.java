package final_project.momeasy.domain.home_cam.service;

import final_project.momeasy.domain.home_cam.converter.HomecamConverter;
import final_project.momeasy.domain.home_cam.dto.HomecamResponseDTO;
import final_project.momeasy.domain.home_cam.entity.Homecam;
import final_project.momeasy.domain.home_cam.exception.HomecamErrorCode;
import final_project.momeasy.domain.home_cam.exception.HomecamException;
import final_project.momeasy.domain.home_cam.repository.HomecamRepository;
import final_project.momeasy.domain.parent.entity.Parent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomecamQueryServiceImpl implements HomecamQueryService {
    private final HomecamRepository homecamRepository;

    @Override
    public HomecamResponseDTO.HomecamDTO getHomecamById(Long homecamId, Parent parent) {
        Homecam homecam = homecamRepository.findById(homecamId).orElseThrow(()->new HomecamException(HomecamErrorCode.NOT_FOUND));
        if(!homecam.getParent().equals(parent)) {
            throw new HomecamException(HomecamErrorCode.UNAUTHORIZED_ACCESS);
        }
        return HomecamConverter.toHomecamDTO(homecam);
    }

    @Override
    public HomecamResponseDTO.HomecamVideoDTO getHomecamVideoById(Long homecamId, Parent parent) {
        Homecam homecam = homecamRepository.findById(homecamId).orElseThrow(()->new HomecamException(HomecamErrorCode.NOT_FOUND));
        if(!homecam.getParent().equals(parent)) {
            throw new HomecamException(HomecamErrorCode.UNAUTHORIZED_ACCESS);
        }
        return HomecamConverter.toHomecamVideoDTO(homecam);
    }

    @Override
    public List<HomecamResponseDTO.HomecamDTO> getHomecamListByParent(Parent parent) {
        List<Homecam> homecamList = homecamRepository.findAllByParentId(parent.getId());
        if(homecamList.isEmpty()){
            throw new HomecamException(HomecamErrorCode.NOT_FOUND);
        }
        List<HomecamResponseDTO.HomecamDTO> homecamListDto
        = homecamList.stream().map(HomecamConverter::toHomecamDTO).toList();
        return homecamListDto;
    }
}
