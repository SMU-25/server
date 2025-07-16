package final_project.momeasy.domain.parent.service.query;

import final_project.momeasy.domain.parent.converter.ParentConverter;
import final_project.momeasy.domain.parent.dto.response.ParentResponseDTO;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.parent.exception.ParentErrorCode;
import final_project.momeasy.domain.parent.exception.ParentException;
import final_project.momeasy.domain.parent.repository.ParentRepository;
import final_project.momeasy.global.util.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParentQueryServiceImpl implements ParentQueryService {

    private final ParentRepository parentRepository;
    private final S3Uploader s3Uploader;

    @Override
    public ParentResponseDTO.ParentDetailResponseDTO getParentDetail(Long parentId) {
        Parent parent = parentRepository.findByIdNotDeleted(parentId)
                .orElseThrow(() -> new ParentException(ParentErrorCode.NOT_FOUND));

        String profileImagePath = parent.getProfileImage();
        String profileImageUrl = (profileImagePath != null)
                ? s3Uploader.getPresignedUrl(profileImagePath)
                : null;

        return ParentConverter.toParentDetailResponseDTO(parent, profileImageUrl);
    }
}
