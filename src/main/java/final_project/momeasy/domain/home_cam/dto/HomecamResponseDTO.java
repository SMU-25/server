package final_project.momeasy.domain.home_cam.dto;

import final_project.momeasy.domain.fever_graph.dto.FeverGraphResponseDTO;
import final_project.momeasy.domain.humidity_graph.dto.HumidityGraphResponseDTO;
import final_project.momeasy.domain.temperature_graph.dto.TemperatureGraphResponseDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class HomecamResponseDTO {
    @Getter
    @Builder
    public static class HomecamDTO {
        private Long homecamId;
        private String name;
        private String place;
        private Long childId;
        private String childName;
        private String serialNum;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    public static class HomecamDetailDTO {
        private String name;
        private String place;
        private Long childId;
        private String childName;
        private String serialNum;
        private String video_url;
        private LocalDateTime createdAt;
    }

    @Getter
    @Builder
    public static class HomecamGraphDTO {
        private GraphGroupDTO day1;
        private GraphGroupDTO day3;
        private GraphGroupDTO day7;
        @Getter
        @Builder
        public static class GraphGroupDTO {
            private List<FeverGraphResponseDTO.FeverGraphHomecamViewDTO> fever;
            private List<HumidityGraphResponseDTO.HumidityGraphHomecamViewDTO> humidity;
            private List<TemperatureGraphResponseDTO.TemperatureGraphHomecamViewDTO> temperature;
        }
    }
}
