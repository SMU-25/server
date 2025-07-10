package final_project.momeasy.domain.illness.converter;

import final_project.momeasy.domain.illness.dto.IllnessResponse;
import final_project.momeasy.domain.illness.entity.Illness;

public class IllnessConverter {

    public static IllnessResponse toDto(Illness illness) {
        return new IllnessResponse(
                illness.getIllnessType().name(),
                illness.getIllnessType().getIllness()
        );
    }
}
