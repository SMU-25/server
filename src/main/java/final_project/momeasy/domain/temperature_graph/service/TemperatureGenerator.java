package final_project.momeasy.domain.temperature_graph.service;

import final_project.momeasy.common.enums.DayRange;
import final_project.momeasy.domain.fever_report.entity.FeverReport;
import final_project.momeasy.domain.temperature_graph.dto.TemperatureGraphResponseDTO;
import final_project.momeasy.domain.temperature_graph.entity.TemperatureGraph;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TemperatureGenerator {
    private final AvgTemperature avgTemperature;

    public TemperatureGraph TemperatureGraph(FeverReport feverReport, int dayOffset, DayRange dayRange, int startHour, int endHour, Long childId) {
        float avg = dayRange == DayRange.DAY1 ? avgTemperature.getTemperatureAvgBy3Hour(startHour, childId) : avgTemperature.getTemperatureAvgByDayAndTimeRange(dayOffset, startHour, endHour, childId);
        TemperatureGraph temperatureGraph = TemperatureGraph.builder()
                .avgTemperature(avg)
                .dayRange(dayRange)
                .build();
        temperatureGraph.setFeverReport(feverReport);
        return temperatureGraph;
    }

    public TemperatureGraphResponseDTO.TemperatureGraphHomecamViewDTO HomecamTemperatureGraph(int dayOffset, DayRange dayRange, int startHour, int endHour, Long childId) {
        float avg = dayRange == DayRange.DAY1 ? avgTemperature.getTemperatureAvgBy3Hour(startHour, childId) : avgTemperature.getTemperatureAvgByDayAndTimeRange(dayOffset, startHour, endHour, childId);
        return TemperatureGraphResponseDTO.TemperatureGraphHomecamViewDTO.builder()
                .avgtemperature(avg)
                .dayRange(dayRange)
                .build();

    }
}
