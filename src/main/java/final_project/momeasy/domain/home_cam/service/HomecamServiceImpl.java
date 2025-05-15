package final_project.momeasy.domain.home_cam.service;

import final_project.momeasy.domain.home_cam.converter.HomecamConverter;
import final_project.momeasy.domain.home_cam.dto.HomecamRequestDTO;
import final_project.momeasy.domain.home_cam.dto.HomecamResponseDTO;
import final_project.momeasy.domain.home_cam.entity.Homecam;
import final_project.momeasy.domain.home_cam.exception.HomecamErrorCode;
import final_project.momeasy.domain.home_cam.exception.HomecamException;
import final_project.momeasy.domain.home_cam.repository.HomecamRepository;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.parent.exception.ParentErrorCode;
import final_project.momeasy.domain.parent.exception.ParentException;
import final_project.momeasy.domain.parent.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HomecamServiceImpl implements HomecamService {
    private final HomecamRepository homecamRepository;
    private final ParentRepository parentRepository;

    @Override
    public void deleteHomecam(Long homecamId,Parent parent) {
        Homecam homecam = homecamRepository.findById(homecamId).orElseThrow(() -> new HomecamException(HomecamErrorCode.NOT_FOUND));
        if(homecam.getParent().equals(parent)) {
            throw new HomecamException(HomecamErrorCode.UNAUTHORIZED_ACCESS);
        }
        homecamRepository.delete(homecam);
    }

    @Override
    public HomecamResponseDTO.HomecamDTO createHomecam(HomecamRequestDTO.HomecamRegisterDTO homecamRequestDTO, Parent parent) {
        Parent findparent = parentRepository.findById(parent.getId()).orElseThrow(() -> new ParentException(ParentErrorCode.NOT_FOUND));
        // parent 예외 고치기
        Homecam homecam = HomecamConverter.toHomecam(homecamRequestDTO,findparent);
        homecam.setParent(findparent);
        homecamRepository.save(homecam);
        return HomecamConverter.toHomecamDTO(homecam);
    }
}
