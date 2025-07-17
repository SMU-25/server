package final_project.momeasy.domain.child.service.command;

import final_project.momeasy.common.enums.IllnessType;
import final_project.momeasy.domain.child.converter.ChildConverter;
import final_project.momeasy.domain.child.dto.request.ChildRequestDTO;
import final_project.momeasy.domain.child.dto.response.ChildResponseDTO;
import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.child.exception.ChildErrorCode;
import final_project.momeasy.domain.child.exception.ChildException;
import final_project.momeasy.domain.child.repository.ChildIllnessRepository;
import final_project.momeasy.domain.child.repository.ChildRepository;
import final_project.momeasy.domain.illness.entity.Illness;
import final_project.momeasy.domain.illness.repository.IllnessRepository;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.parent.exception.ParentErrorCode;
import final_project.momeasy.domain.parent.exception.ParentException;
import final_project.momeasy.domain.parent.repository.ParentRepository;
import final_project.momeasy.global.util.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class ChildCommandServiceImpl implements ChildCommandService {

    private final ChildRepository childRepository;
    private final ParentRepository parentRepository;
    private final IllnessRepository illnessRepository;
    private final ChildIllnessRepository childIllnessRepository;
    private final S3Uploader s3Uploader;

    @Override
    public ChildResponseDTO.ChildCreateResponseDTO createChild(
            ChildRequestDTO.ChildCreateRequestDTO dto,
            Long parentId) {

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
    public void updateChild(
            Long childId,
            Parent parent,
            ChildRequestDTO.ChildUpdateRequestDTO dto
    ) {
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new ChildException(ChildErrorCode.NOT_FOUND));

        if (!childRepository.existsByChildIdAndParentId(childId, parent.getId())) {
            throw new ChildException(ChildErrorCode.UNAUTHORIZED_ACCESS);
        }

        // update
        child.update(dto);

        // 기존 연관관계 제거
        childIllnessRepository.deleteByChild(child);
        // 영속성 컨텍스트에서 연관관계 삭제
        child.getChildIllnesses().clear();

        // child-illness 연관관계 >새롭게< 설정
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

    @Override
    public String updateChildProfileImage(Long childId, Parent parent, MultipartFile profileImage) throws IOException {
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new ChildException(ChildErrorCode.NOT_FOUND));

        if (!childRepository.existsByChildIdAndParentId(childId, parent.getId())) {
            throw new ChildException(ChildErrorCode.UNAUTHORIZED_ACCESS);
        }

        // 기존 이미지 있으면 삭제
        if (child.getProfileImage() != null) {
            s3Uploader.delete(child.getProfileImage());
        }

        // 새로운 이미지 업로드
        String fileName = s3Uploader.upload(profileImage, "profile");
        child.updateProfileImage(fileName);

        childRepository.save(child);

        return s3Uploader.getPresignedUrl(fileName);

    }
}
