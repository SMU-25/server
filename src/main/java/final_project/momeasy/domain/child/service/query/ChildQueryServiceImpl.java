package final_project.momeasy.domain.child.service.query;

import final_project.momeasy.domain.child.converter.ChildConverter;
import final_project.momeasy.domain.child.dto.response.ChildResponseDTO;
import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.child.exception.ChildErrorCode;
import final_project.momeasy.domain.child.exception.ChildException;
import final_project.momeasy.domain.child.repository.ChildRepository;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.util.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChildQueryServiceImpl implements ChildQueryService {

    private final ChildRepository childRepository;
    private final S3Uploader s3Uploader;

    @Override
    public ChildResponseDTO.ChildDetailResponseDTO getChild(Long childId, Parent parent) {
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new ChildException(ChildErrorCode.NOT_FOUND));

        if (!childRepository.existsByChildIdAndParentId(childId, parent.getId())) {
            throw new ChildException(ChildErrorCode.UNAUTHORIZED_ACCESS);
        }

        String profileImagePath = parent.getProfileImage();
        String profileImageUrl = (profileImagePath != null)
                ? s3Uploader.getPresignedUrl(profileImagePath)
                : null;

        return ChildConverter.toChildDetailResponseDTO(child, profileImageUrl);
    }

    @Override
    public List<ChildResponseDTO.ChildSimpleResponseDTO> getChildren(Parent parent) {
        List<Child> children = childRepository.findByParentId(parent.getId());

        return children.stream()
                .map(child -> {
                    String profileImagePath = parent.getProfileImage();
                    String profileImageUrl = (profileImagePath != null)
                            ? s3Uploader.getPresignedUrl(profileImagePath)
                            : null;

                    return ChildConverter.toChildSimpleResponseDTO(child, profileImageUrl);
                })
                .collect(Collectors.toList());
    }
}
