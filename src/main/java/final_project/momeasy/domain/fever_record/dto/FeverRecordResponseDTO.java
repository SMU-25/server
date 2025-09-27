package final_project.momeasy.domain.fever_record.dto;

import final_project.momeasy.common.enums.RecordState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class FeverRecordResponseDTO {
    @Getter
    @Builder
    public static class FeverRecordStateViewDTO{
        private float fever;
        private RecordState state;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    public static class FeverRecordViewDTO{
        private float fever;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    public static class FeverRecordListViewDTO{
        private List<FeverRecordViewDTO> feverRecords;
        private Boolean hasNext;
        private Long cursor;
    }

    @Getter
    @Builder
    public static class FeverRecordGraphDTO{
        @Schema(description = "특정 날짜 or 시간의 평균 체온")
        private float avgfever;
    }
}
