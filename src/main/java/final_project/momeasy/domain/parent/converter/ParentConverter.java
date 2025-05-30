package final_project.momeasy.domain.parent.converter;

import final_project.momeasy.common.enums.Role;
import final_project.momeasy.domain.parent.dto.request.ParentRequestDTO;
import final_project.momeasy.domain.parent.dto.response.ParentResponseDTO;
import final_project.momeasy.domain.parent.entity.Parent;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParentConverter {

    public static Parent toParent(
            ParentRequestDTO.ParentCreateRequestDTO parentCreateRequestDTO,
            PasswordEncoder passwordEncoder
    ) {
        return Parent.builder()
                .name(parentCreateRequestDTO.getName())
                .password(passwordEncoder.encode(parentCreateRequestDTO.getPassword()))
                .email(parentCreateRequestDTO.getEmail())
                .birthdate(parentCreateRequestDTO.getBirthdate())
                .socialType(parentCreateRequestDTO.getSocialType())
                .gender(parentCreateRequestDTO.getGender())
                .role(Role.USER)
                .build();
    }

    public static ParentResponseDTO.ParentCreateResponseDTO toParentResponseDTO(Parent parent) {
        return ParentResponseDTO.ParentCreateResponseDTO.builder()
                .id(parent.getId())
                .createdAt(parent.getCreatedAt())
                .build();
    }
}
