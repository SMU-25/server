package final_project.momeasy.domain.fever_report.dto;

import final_project.momeasy.common.enums.IllnessType;
import final_project.momeasy.common.enums.SymptomType;
import final_project.momeasy.domain.fever_graph.dto.FeverGraphResponseDTO;
import final_project.momeasy.domain.humidity_graph.dto.HumidityGraphResponseDTO;
import final_project.momeasy.domain.temperature_graph.dto.TemperatureGraphResponseDTO;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class FeverReportResponseDTO {
    @Getter
    @Builder
    public static class FeverReportViewDTO {
        private Long reportId;

        private List<SymptomType> symptoms;

        private String etc_symptom;

        private String outing;

        private String special;

        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    public static class FeverReportListViewDTO {
        List<FeverReportViewDTO> feverReports;
        private boolean hasNext;
        private Long cursor;
    }

    @Getter
    @Builder
    public static class FeverReportDetailViewDTO {

        private List<SymptomType> symptoms;
        private List<IllnessType> illnesses;
        private String etc_symptom;
        private String outing;
        private String special;
        private LocalDateTime createdAt;

        private GraphGroupDTO day1;
        private GraphGroupDTO day3;
        private GraphGroupDTO day7;

        @Getter
        @Builder
        public static class GraphGroupDTO {
            private List<FeverGraphResponseDTO.FeverGraphViewDTO> fever;
            private List<HumidityGraphResponseDTO.HumidityGraphViewDTO> humidity;
            private List<TemperatureGraphResponseDTO.TemperatureGraphViewDTO> temperature;
        }
    }

    @Getter
    @Builder
    public static class FeverReportCreateDTO {
        private Long reportId;
        private List<SymptomType> symptoms;
        private List<IllnessType> illnesses;
        private String etc_symptom;
        private String outing;
        private String special;
        private LocalDateTime createdAt;

        private GraphGroupDTO day1;
        private GraphGroupDTO day3;
        private GraphGroupDTO day7;

        @Getter
        @Builder
        public static class GraphGroupDTO {
            private List<FeverGraphResponseDTO.FeverGraphCreateDTO> fever;
            private List<HumidityGraphResponseDTO.HumidityGraphCreateDTO> humidity;
            private List<TemperatureGraphResponseDTO.TemperatureGraphCreateDTO> temperature;
        }
    }
}
