package final_project.momeasy.domain.parent.service.query;

import final_project.momeasy.domain.parent.converter.ParentConverter;
import final_project.momeasy.domain.parent.dto.response.ParentResponseDTO;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.parent.exception.ParentErrorCode;
import final_project.momeasy.domain.parent.exception.ParentException;
import final_project.momeasy.domain.parent.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParentQueryServiceImpl implements ParentQueryService {

    private final ParentRepository parentRepository;

    @Override
    public ParentResponseDTO.ParentDetailResponseDTO getParentDetail(Long parentId) {
        Parent parent = parentRepository.findById(parentId)
                .orElseThrow(() -> new ParentException(ParentErrorCode.NOT_FOUND));

        return ParentConverter.toParentDetailResponseDTO(parent);
    }
}
