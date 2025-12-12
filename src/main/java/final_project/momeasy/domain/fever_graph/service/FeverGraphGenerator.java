package final_project.momeasy.domain.fever_graph.service;

import final_project.momeasy.common.enums.DayRange;
import final_project.momeasy.domain.fever_graph.dto.FeverGraphResponseDTO;
import final_project.momeasy.domain.fever_graph.entity.FeverGraph;
import final_project.momeasy.domain.fever_report.entity.FeverReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeverGraphGenerator {
    private final AvgFever avgFever;

    public FeverGraph FeverGraph(FeverReport feverReport, int dayOffset, DayRange dayRange, int startHour, int endHour, Long childId) {
        float avg = dayRange == DayRange.DAY1 ? avgFever.getFeverAvgBy3Hour(startHour, childId) : avgFever.getFeverAvgByDayAndTimeRange(dayOffset, startHour, endHour, childId);
        FeverGraph feverGraph = FeverGraph.builder()
                .avgFever(avg)
                .dayRange(dayRange)
                .build();
        feverGraph.setFeverReport(feverReport);
        return feverGraph;
    }

    public FeverGraphResponseDTO.FeverGraphHomecamViewDTO HomecamFeverGraph(int dayOffset, DayRange dayRange, int startHour, int endHour, Long childId) {
        float avg = dayRange == DayRange.DAY1 ? avgFever.getFeverAvgBy3Hour(startHour, childId) : avgFever.getFeverAvgByDayAndTimeRange(dayOffset, startHour, endHour, childId);
        return FeverGraphResponseDTO.FeverGraphHomecamViewDTO.builder()
                .avgfever(avg)
                .dayRange(dayRange)
                .build();
    }
}
