package final_project.momeasy.domain.child.service.command;

import final_project.momeasy.common.enums.IllnessType;
import final_project.momeasy.domain.child.converter.ChildConverter;
import final_project.momeasy.domain.child.dto.request.ChildRequestDTO;
import final_project.momeasy.domain.child.dto.response.ChildResponseDTO;
import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.child.exception.ChildErrorCode;
import final_project.momeasy.domain.child.exception.ChildException;
import final_project.momeasy.domain.child.repository.ChildRepository;
import final_project.momeasy.domain.illness.entity.Illness;
import final_project.momeasy.domain.illness.repository.IllnessRepository;
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
public class ChildCommandServiceImpl implements ChildCommandService {

    private final ChildRepository childRepository;
    private final ParentRepository parentRepository;
    private final IllnessRepository illnessRepository;

    @Override
    public ChildResponseDTO.ChildCreateResponseDTO createChild(ChildRequestDTO.ChildCreateRequestDTO dto, Long parentId) {

        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new ParentException(ParentErrorCode.NOT_FOUND));

        Child child = ChildConverter.toChild(dto);

        // TODO: 아이 중복 추가 예외 처리

        childRepository.save(child);

        parent.addChild(child); // parent-child 연관관계 설정

        // child-illness 연관관계 설정
        for (IllnessType illnessType : dto.illnessTypes()) {
            Illness illness = illnessRepository.findByIllnessType(illnessType)
                    .orElseGet(() -> illnessRepository.save(
                            Illness.builder()
                                    .illnessType(illnessType)
                                    .build()
                    ));

            child.addIllness(illness);
        }

        return ChildConverter.toChildCreateResponseDTO(child);

    }

    @Override
    public void deleteChild(Long childId, Parent parent) {
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new ChildException(ChildErrorCode.NOT_FOUND));

        if (!childRepository.existsByChildIdAndParentId(childId, parent.getId())) {
            throw new ChildException(ChildErrorCode.UNAUTHORIZED_ACCESS);
        }

        child.delete();
    }

    @Override
    public void updateChild(Long childId, Parent parent, ChildRequestDTO.ChildUpdateRequestDTO dto) {
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new ChildException(ChildErrorCode.NOT_FOUND));

        if (!childRepository.existsByChildIdAndParentId(childId, parent.getId())) {
            throw new ChildException(ChildErrorCode.UNAUTHORIZED_ACCESS);
        }

        // update
        child.update(dto);

        child.getChildIllnesses().clear(); // 기존 연관관계 제거

        // child-illness 연관관계 설정
        for (IllnessType illnessType : dto.illnessTypes()) {
            Illness illness = illnessRepository.findByIllnessType(illnessType)
                    .orElseGet(() -> illnessRepository.save(
                            Illness.builder()
                                    .illnessType(illnessType)
                                    .build()
                    ));

            child.addIllness(illness);
        }

    }
}
