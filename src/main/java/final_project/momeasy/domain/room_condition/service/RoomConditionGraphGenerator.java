package final_project.momeasy.domain.room_condition.service;

import final_project.momeasy.domain.humidity_graph.service.AvgHumidity;
import final_project.momeasy.domain.room_condition.dto.RoomConditionResponseDTO;
import final_project.momeasy.domain.temperature_graph.service.AvgTemperature;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoomConditionGraphGenerator {
    private final AvgHumidity avgHumidity;
    private final AvgTemperature avgTemperature;

    public List<RoomConditionResponseDTO.RoomConditionGrpahDTO> createDay1(Long childId) {
        List<RoomConditionResponseDTO.RoomConditionGrpahDTO> result = new ArrayList<>();
        for (int t = 0; t < 24; t += 3) {
            result.add(RoomConditionResponseDTO.RoomConditionGrpahDTO.builder()
                    .avgtemperature(avgTemperature.getTemperatureAvgBy3Hour(t, childId))
                    .avghumidity(avgHumidity.getHumidityAvgBy3Hour(t, childId))
                    .build());
        }
        return result;
    }

    public List<RoomConditionResponseDTO.RoomConditionGrpahDTO> createDay3(Long childId) {
        List<RoomConditionResponseDTO.RoomConditionGrpahDTO> result = new ArrayList<>();
        for (int d = 2; d >= 0; d--) {
            // 새벽: 00:00 ~ 06:00
            result.add(RoomConditionResponseDTO.RoomConditionGrpahDTO.builder()
                    .avgtemperature(avgTemperature.getTemperatureAvgByDayAndTimeRange(d, 0, 6, childId))
                    .avghumidity(avgHumidity.getHumidityAvgByDayAndTimeRange(d, 0, 6, childId))
                    .build());

            // 오전: 06:00 ~ 12:00
            result.add(RoomConditionResponseDTO.RoomConditionGrpahDTO.builder()
                    .avgtemperature(avgTemperature.getTemperatureAvgByDayAndTimeRange(d, 6, 12, childId))
                    .avghumidity(avgHumidity.getHumidityAvgByDayAndTimeRange(d, 6, 12, childId))
                    .build());

            // 오후: 12:00 ~ 24:00
            result.add(RoomConditionResponseDTO.RoomConditionGrpahDTO.builder()
                    .avgtemperature(avgTemperature.getTemperatureAvgByDayAndTimeRange(d, 12, 24, childId))
                    .avghumidity(avgHumidity.getHumidityAvgByDayAndTimeRange(d, 12, 24, childId))
                    .build());
        }
        return result;
    }

    public List<RoomConditionResponseDTO.RoomConditionGrpahDTO> createDay7(Long childId) {
        List<RoomConditionResponseDTO.RoomConditionGrpahDTO> result = new ArrayList<>();
        for (int d = 6; d >= 0; d--) {
            result.add(RoomConditionResponseDTO.RoomConditionGrpahDTO.builder()
                    .avgtemperature(avgTemperature.getTemperatureAvgByDayAndTimeRange(d, 0, 24, childId))
                    .avghumidity(avgHumidity.getHumidityAvgByDayAndTimeRange(d, 0, 24, childId))
                    .build());
        }
        return result;
    }
}
