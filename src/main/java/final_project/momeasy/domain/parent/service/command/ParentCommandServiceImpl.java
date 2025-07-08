package final_project.momeasy.domain.parent.service.command;

import final_project.momeasy.common.enums.SocialType;
import final_project.momeasy.domain.parent.converter.ParentConverter;
import final_project.momeasy.domain.parent.dto.request.ParentRequestDTO;
import final_project.momeasy.domain.parent.dto.response.ParentResponseDTO;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.parent.exception.ParentErrorCode;
import final_project.momeasy.domain.parent.exception.ParentException;
import final_project.momeasy.domain.parent.repository.ParentRepository;
import final_project.momeasy.global.auth.exception.AuthErrorCode;
import final_project.momeasy.global.auth.exception.AuthException;
import final_project.momeasy.global.util.mail.verification.exception.VerificationCodeErrorCode;
import final_project.momeasy.global.util.mail.verification.exception.VerificationCodeException;
import final_project.momeasy.global.util.mail.verification.service.VerificationCodeService;
import final_project.momeasy.global.util.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ParentCommandServiceImpl implements ParentCommandService {

    private final ParentRepository parentRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationCodeService verificationCodeService;
    private final S3Uploader s3Uploader;

    @Override
    public ParentResponseDTO.ParentCreateResponseDTO createParent(ParentRequestDTO.ParentCreateRequestDTO dto) {
        if (parentRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new ParentException(ParentErrorCode.DUPLICATE_EMAIL);
        }

        if (!verificationCodeService.isVerified(dto.getEmail())) {
            throw new VerificationCodeException(VerificationCodeErrorCode.CODE_NOT_VERIFIED);
        }

        // DTO -> Parent
        Parent parent = ParentConverter.toParent(dto, passwordEncoder);

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

    @Override
    public void updateParent(Long parentId, ParentRequestDTO.ParentUpdateRequestDTO dto) {
        Parent parent = parentRepository.findByIdNotDeleted(parentId)
                .orElseThrow(() -> new ParentException(ParentErrorCode.NOT_FOUND));

        if (parent.getSocialType() == SocialType.LOCAL) {
            if (dto.newPassword() != null) {
                parent.updatePassword(passwordEncoder.encode(dto.newPassword()));
            }
        } else {
            if (dto.newPassword() != null) {
                throw new AuthException(AuthErrorCode.SOCIAL_USER_CANNOT_UPDATE_PASSWORD);
            }
        }

        parent.updateInfo(dto.name(), dto.birthdate(), dto.gender());

        parentRepository.save(parent);
    }

    @Override
    public String updateProfileImage(Long parentId, MultipartFile profileImage) throws IOException {
        Parent parent = parentRepository.findByIdNotDeleted(parentId)
                .orElseThrow(() -> new ParentException(ParentErrorCode.NOT_FOUND));

        if (profileImage == null || profileImage.isEmpty()) {
            throw new ParentException(ParentErrorCode.IMAGE_NOT_PROVIDED);
        }

        // 기존 이미지 있으면 삭제
        if (parent.getProfileImage() != null) {
            s3Uploader.delete(parent.getProfileImage());
        }

        // 새로운 이미지 업로드
        String fileName = s3Uploader.upload(profileImage, "profile");
        parent.updateProfileImage(fileName);

        parentRepository.save(parent);

        return s3Uploader.getPresignedUrl(fileName);
    }
}
