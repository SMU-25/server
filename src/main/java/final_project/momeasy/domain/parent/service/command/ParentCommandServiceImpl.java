package final_project.momeasy.domain.parent.service.command;

import final_project.momeasy.domain.parent.converter.ParentConverter;
import final_project.momeasy.domain.parent.dto.request.ParentRequestDTO;
import final_project.momeasy.domain.parent.dto.response.ParentResponseDTO;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.parent.exception.ParentErrorCode;
import final_project.momeasy.domain.parent.exception.ParentException;
import final_project.momeasy.domain.parent.repository.ParentRepository;
import final_project.momeasy.global.util.mail.verification.exception.VerificationCodeErrorCode;
import final_project.momeasy.global.util.mail.verification.exception.VerificationCodeException;
import final_project.momeasy.global.util.mail.verification.service.VerificationCodeService;
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
    private final VerificationCodeService verificationCodeService;

    @Override
    public ParentResponseDTO.ParentCreateResponseDTO createParent(ParentRequestDTO.ParentCreateRequestDTO parentCreateRequestDTO) {
        if (parentRepository.findByEmail(parentCreateRequestDTO.getEmail()).isPresent()) {
            throw new ParentException(ParentErrorCode.DUPLICATE_EMAIL);
        }

        if (!verificationCodeService.isVerified(parentCreateRequestDTO.getEmail())) {
            throw new VerificationCodeException(VerificationCodeErrorCode.CODE_NOT_VERIFIED);
        }

        // DTO -> Parent
        Parent parent = ParentConverter.toParent(parentCreateRequestDTO, passwordEncoder);

        // DB에 저장
        parentRepository.save(parent);

        // response DTO로 변환
        return ParentConverter.toParentResponseDTO(parent);

    }

    @Override
    public void deleteParent(Long parentId) {
        Parent parent = parentRepository.findByIdNotDeleted(parentId)
                .orElseThrow(() -> new ParentException(ParentErrorCode.NOT_FOUND));

        parent.softDelete();
    }

}
