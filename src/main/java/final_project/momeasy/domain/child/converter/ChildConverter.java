package final_project.momeasy.domain.child.converter;

import final_project.momeasy.domain.child.dto.request.ChildRequestDTO;
import final_project.momeasy.domain.child.dto.response.ChildResponseDTO;
import final_project.momeasy.domain.child.entity.Child;

public class ChildConverter {

    public static Child toChild(ChildRequestDTO.ChildCreateRequestDTO dto) {
        return Child.builder()
                .name(dto.name())
                .birthdate(dto.birthdate())
                .height(dto.height())
                .weight(dto.weight())
                .gender(dto.gender())
                .seizure(dto.seizure())
                .build();
    }

    public static ChildResponseDTO.ChildCreateResponseDTO toChildCreateResponseDTO(Child child) {
        return ChildResponseDTO.ChildCreateResponseDTO.builder()
                .id(child.getId())
                .createdAt(child.getCreatedAt())
                .build();
    }
}
