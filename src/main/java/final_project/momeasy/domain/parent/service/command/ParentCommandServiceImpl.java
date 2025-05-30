package final_project.momeasy.domain.parent.service.command;

import final_project.momeasy.domain.parent.converter.ParentConverter;
import final_project.momeasy.domain.parent.dto.request.ParentRequestDTO;
import final_project.momeasy.domain.parent.dto.response.ParentResponseDTO;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.parent.repository.ParentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ParentCommandServiceImpl implements ParentCommandService {

    private final ParentRepository parentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ParentResponseDTO.ParentCreateResponseDTO createParent(ParentRequestDTO.ParentCreateRequestDTO parentCreateRequestDTO) {
        // DTO -> Parent
        Parent parent = ParentConverter.toParent(parentCreateRequestDTO, passwordEncoder);

        // DB에 저장
        parentRepository.save(parent);

        // response DTO로 변환
        return ParentConverter.toParentResponseDTO(parent);

    }
}
