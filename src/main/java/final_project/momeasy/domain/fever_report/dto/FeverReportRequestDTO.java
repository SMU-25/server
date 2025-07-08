package final_project.momeasy.domain.fever_report.dto;

import final_project.momeasy.common.enums.SymptomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class FeverReportRequestDTO {
    @Getter
    @Builder
    public static class FeverReportCreateDTO {
        @NotBlank(message = "증상은 필수 입력 값입니다.")
        private List<SymptomType> symptoms;

        private String etc_symptom;

        @NotNull(message = "외출 기록은 필수 입력 값입니다.")
        private String outing;
    }

    @Getter
    @Builder
    public static class FeverReportUpdateDTO {
        @NotBlank(message = "증상은 필수 입력 값입니다.")
        private List<SymptomType> symptoms;

        private String etc_symptom;

        @NotNull(message = "외출 기록은 필수 입력 값입니다.")
        private String outing;
    }
}
