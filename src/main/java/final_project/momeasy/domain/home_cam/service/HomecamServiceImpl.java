package final_project.momeasy.domain.home_cam.service;

import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.child.exception.ChildErrorCode;
import final_project.momeasy.domain.child.exception.ChildException;
import final_project.momeasy.domain.child.repository.ChildRepository;
import final_project.momeasy.domain.home_cam.converter.HomecamConverter;
import final_project.momeasy.domain.home_cam.dto.HomecamRequestDTO;
import final_project.momeasy.domain.home_cam.dto.HomecamResponseDTO;
import final_project.momeasy.domain.home_cam.entity.Homecam;
import final_project.momeasy.domain.home_cam.exception.HomecamErrorCode;
import final_project.momeasy.domain.home_cam.exception.HomecamException;
import final_project.momeasy.domain.home_cam.repository.HomecamRepository;
import final_project.momeasy.domain.parent.entity.Parent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class HomecamServiceImpl implements HomecamService {
    private final HomecamRepository homecamRepository;
    private final ChildRepository childRepository;

    @Override
    public void deleteHomecam(Long homecamId,Parent parent) {
        Homecam homecam = homecamRepository.findById(homecamId).orElseThrow(()->new HomecamException(HomecamErrorCode.NOT_FOUND));
        if(!homecam.getParent().equals(parent)) {
            throw new HomecamException(HomecamErrorCode.UNAUTHORIZED_ACCESS);
        }
        homecamRepository.delete(homecam);
    }

    @Override
    public HomecamResponseDTO.HomecamDTO createHomecam(HomecamRequestDTO.HomecamCreateDTO homecamRequestDTO, Parent parent, Long childId) {
        Child child = childRepository.findById(childId).orElseThrow(() -> new ChildException(ChildErrorCode.NOT_FOUND));
        if (!childRepository.existsByChildIdAndParentId(childId, parent.getId())) {
            throw new ChildException(ChildErrorCode.UNAUTHORIZED_ACCESS);
        }
        homecamRepository.findByChildId(childId).ifPresent(homecam -> {throw new HomecamException(HomecamErrorCode.ALREADY_HAVE);});
        Homecam homecam = HomecamConverter.toHomecam(homecamRequestDTO,parent);
        homecam.setParent(parent);
        homecam.setChild(child);
        homecamRepository.save(homecam);
        return HomecamConverter.toHomecamDTO(homecam);
    }

    @Override
    public void updateHomecam(HomecamRequestDTO.HomecamUpdateDTO homecamUpdateDTO, Parent parent, Long childId, Long homecamId) {
        Homecam homecam = homecamRepository.findById(homecamId).orElseThrow(()->new HomecamException(HomecamErrorCode.NOT_FOUND));
        Child child = childRepository.findById(childId).orElseThrow(() -> new ChildException(ChildErrorCode.NOT_FOUND));
        if(!homecam.getParent().equals(parent) || !childRepository.existsByChildIdAndParentId(childId, parent.getId())) {
            throw new HomecamException(HomecamErrorCode.UNAUTHORIZED_ACCESS);
        }
        homecam.update(homecamUpdateDTO);
        homecam.setChild(child);
    }
}
