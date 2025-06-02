package final_project.momeasy.domain.fever_record.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class FeverRecordResponseDTO {
    @Getter
    @Builder
    public static class FeverRecordViewDTO{
        private float fever;
        private LocalDateTime createdAt;
    }
}
