package final_project.momeasy.domain.child.service.command;

import final_project.momeasy.domain.child.dto.request.ChildRequestDTO;
import final_project.momeasy.domain.child.dto.response.ChildResponseDTO;
import final_project.momeasy.domain.parent.entity.Parent;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ChildCommandService {

    ChildResponseDTO.ChildCreateResponseDTO createChild(ChildRequestDTO.ChildCreateRequestDTO dto, Long parentId);

    void deleteChild(Long childId, Parent parent);

    void updateChild(Long childId, Parent parent, ChildRequestDTO.ChildUpdateRequestDTO dto);

    String updateChildProfileImage(Long childId, Parent parent, MultipartFile profileImage) throws IOException;
}
