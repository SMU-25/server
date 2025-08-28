package final_project.momeasy.domain.home_cam.converter;

import final_project.momeasy.common.enums.DayRange;
import final_project.momeasy.domain.fever_graph.dto.FeverGraphResponseDTO;
import final_project.momeasy.domain.home_cam.dto.HomecamRequestDTO;
import final_project.momeasy.domain.home_cam.dto.HomecamResponseDTO;
import final_project.momeasy.domain.home_cam.entity.Homecam;
import final_project.momeasy.domain.humidity_graph.dto.HumidityGraphResponseDTO;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.temperature_graph.dto.TemperatureGraphResponseDTO;

import java.util.List;

public class HomecamConverter {
    public static HomecamResponseDTO.HomecamDTO toHomecamDTO(Homecam homecam) {
        return HomecamResponseDTO.HomecamDTO.builder()
                .homecamId(homecam.getId())
                .name(homecam.getName())
                .place(homecam.getPlace())
                .childId(homecam.getChild().getId())
                .childName(homecam.getChild().getName())
                .createdAt(homecam.getCreatedAt())
                .serialNum(homecam.getSerialNum())
                .build();
    }

    public static HomecamResponseDTO.HomecamDetailDTO toHomecamDetailDTO(Homecam homecam) {
        return HomecamResponseDTO.HomecamDetailDTO.builder()
                .name(homecam.getName())
                .place(homecam.getPlace())
                .video_url(homecam.getVideo_url())
                .childId(homecam.getChild().getId())
                .childName(homecam.getChild().getName())
                .createdAt(homecam.getCreatedAt())
                .serialNum(homecam.getSerialNum())
                .build();
    }

    public static Homecam toHomecam(HomecamRequestDTO.HomecamCreateDTO homecamRegisterDTO, Parent parent) {
        return Homecam.builder()
                .name(homecamRegisterDTO.getName())
                .serialNum(homecamRegisterDTO.getSerial_num())
                .place(homecamRegisterDTO.getPlace())
                .parent(parent)
                .build();
    }

    public static HomecamResponseDTO.HomecamGraphDTO toHomecamGraphViewDTO(
            List<FeverGraphResponseDTO.FeverGraphHomecamViewDTO> feverGraphs, List<HumidityGraphResponseDTO.HumidityGraphHomecamViewDTO> humidityGraphs,
            List<TemperatureGraphResponseDTO.TemperatureGraphHomecamViewDTO> temperatureGraphs) {
        return HomecamResponseDTO.HomecamGraphDTO.builder()
                .day1(getGraphGroupViewDTO(feverGraphs,humidityGraphs,temperatureGraphs,DayRange.DAY1))
                .day3(getGraphGroupViewDTO(feverGraphs,humidityGraphs,temperatureGraphs,DayRange.DAY3))
                .day7(getGraphGroupViewDTO(feverGraphs,humidityGraphs,temperatureGraphs,DayRange.DAY7))
                .build();
    }

    private static HomecamResponseDTO.HomecamGraphDTO.GraphGroupDTO getGraphGroupViewDTO(
            List<FeverGraphResponseDTO.FeverGraphHomecamViewDTO> feverGraphs, List<HumidityGraphResponseDTO.HumidityGraphHomecamViewDTO> humidityGraphs,
            List<TemperatureGraphResponseDTO.TemperatureGraphHomecamViewDTO> temperatureGraphs, DayRange dayRange) {
        return HomecamResponseDTO.HomecamGraphDTO.GraphGroupDTO.builder()
                .fever(feverGraphs.stream()
                        .filter(graph -> graph.getDayRange() == dayRange)
                        .toList())
                .temperature(temperatureGraphs.stream()
                        .filter(graph -> graph.getDayRange() == dayRange)
                        .toList())
                .humidity(humidityGraphs.stream()
                        .filter(graph -> graph.getDayRange() == dayRange)
                        .toList())
                .build();
    }
}
