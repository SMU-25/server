package final_project.momeasy.domain.fever_report.dto;

import final_project.momeasy.common.enums.SymptomType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class FeverReportResponseDTO {
    @Getter
    @Builder
    public static class FeverReportViewDTO {
        private List<SymptomType> symptoms;

        private String etc_symptom;

        private String outing;

        private String special;

        private LocalDateTime createdAt;
    }
}
