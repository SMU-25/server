package final_project.momeasy.domain.child.dto.request;

import final_project.momeasy.common.enums.Gender;
import final_project.momeasy.common.enums.IllnessType;
import final_project.momeasy.common.enums.Seizure;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

public class ChildRequestDTO {

    @Builder
    public record ChildCreateRequestDTO (
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
