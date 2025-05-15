package final_project.momeasy.domain.fever_record.dto;

import lombok.Builder;
import lombok.Getter;

public class FeverRecordRequestDTO {
    @Getter
    @Builder
    public static class FeverRecordCreateDTO{
        private float fever;
    }
}
