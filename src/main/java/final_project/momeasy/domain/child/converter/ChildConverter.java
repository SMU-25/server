package final_project.momeasy.domain.child.converter;

import final_project.momeasy.domain.child.dto.request.ChildRequestDTO;
import final_project.momeasy.domain.child.dto.response.ChildResponseDTO;
import final_project.momeasy.domain.child.entity.Child;

import java.util.stream.Collectors;

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

    public static ChildResponseDTO.ChildDetailResponseDTO toChildDetailResponseDTO(Child child) {
        return ChildResponseDTO.ChildDetailResponseDTO.builder()
                .name(child.getName())
                .birthdate(child.getBirthdate())
                .height(child.getHeight())
                .weight(child.getWeight())
                .gender(child.getGender())
                .seizure(child.getSeizure())
                .profileImage(child.getProfileImage())
                .illnessTypes(child.getChildIllnesses().stream()
                        .map(childIllness -> childIllness.getIllness().getIllnessType())
                        .collect(Collectors.toUnmodifiableList()))
                .build();
    }

    public static ChildResponseDTO.ChildSimpleResponseDTO toChildSimpleResponseDTO(Child child) {
        return ChildResponseDTO.ChildSimpleResponseDTO.builder()
                .childId(child.getId())
                .name(child.getName())
                .profileImage(child.getProfileImage())
                .build();
    }

}
