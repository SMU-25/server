package final_project.momeasy.domain.parent.service.command;

import final_project.momeasy.domain.parent.dto.request.ParentRequestDTO;
import final_project.momeasy.domain.parent.dto.response.ParentResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ParentCommandService {

    ParentResponseDTO.ParentCreateResponseDTO createParent(ParentRequestDTO.ParentCreateRequestDTO parentCreateRequestDTO);

    void deleteParent(Long parentId);

    void updateParent(Long parentId, ParentRequestDTO.ParentUpdateRequestDTO dto);

    String updateProfileImage(Long parentId, MultipartFile profileImage) throws IOException;
}
