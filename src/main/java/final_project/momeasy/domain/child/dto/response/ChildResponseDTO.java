package final_project.momeasy.domain.child.dto.response;

import final_project.momeasy.common.enums.Gender;
import final_project.momeasy.common.enums.IllnessType;
import final_project.momeasy.common.enums.Seizure;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ChildResponseDTO {

    @Builder
    public record ChildCreateResponseDTO(
            Long id,
            LocalDateTime createdAt
    ) {
    }

    @Builder
    public record ChildDetailResponseDTO(
            String name,
            LocalDate birthdate,
            Float height,
            Float weight,
            Gender gender,
            Seizure seizure,
            List<IllnessType> illnessTypes
    ) {
    }
}
