package final_project.momeasy.domain.fever_report.dto;

import final_project.momeasy.domain.symptom.entity.Symptom;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class FeverReportResponseDTO {
    @Getter
    @Builder
    public static class FeverReportViewDTO {
        private List<Symptom> symptoms;

        private String etc_symptom;

        private String outing;

        private String special;
    }
}
