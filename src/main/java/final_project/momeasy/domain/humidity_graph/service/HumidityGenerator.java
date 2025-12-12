package final_project.momeasy.domain.humidity_graph.service;

import final_project.momeasy.common.enums.DayRange;
import final_project.momeasy.domain.fever_report.entity.FeverReport;
import final_project.momeasy.domain.humidity_graph.dto.HumidityGraphResponseDTO;
import final_project.momeasy.domain.humidity_graph.entity.HumidityGraph;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HumidityGenerator {
    private final AvgHumidity avgHumidity;

    public HumidityGraph HumidityGraph(FeverReport feverReport, int dayOffset, DayRange dayRange, int startHour, int endHour, Long childId) {
        float avg = dayRange == DayRange.DAY1 ? avgHumidity.getHumidityAvgBy3Hour(startHour, childId) : avgHumidity.getHumidityAvgByDayAndTimeRange(dayOffset, startHour, endHour, childId);
        HumidityGraph humidityGraph = HumidityGraph.builder()
                .avgHumidity(avg)
                .dayRange(dayRange)
                .build();
        humidityGraph.setFeverReport(feverReport);
        return humidityGraph;
    }

    public HumidityGraphResponseDTO.HumidityGraphHomecamViewDTO HomecamHumidityGraph(int dayOffset, DayRange dayRange, int startHour, int endHour, Long childId) {
        float avg = dayRange == DayRange.DAY1 ? avgHumidity.getHumidityAvgBy3Hour(startHour, childId) : avgHumidity.getHumidityAvgByDayAndTimeRange(dayOffset, startHour, endHour, childId);
        return HumidityGraphResponseDTO.HumidityGraphHomecamViewDTO.builder()
                .avghumidity(avg)
                .dayRange(dayRange)
                .build();
    }
}
