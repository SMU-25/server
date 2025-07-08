package final_project.momeasy.domain.temperature_graph.service;

import final_project.momeasy.common.enums.DayRange;
import final_project.momeasy.domain.fever_report.entity.FeverReport;
import final_project.momeasy.domain.fever_report.exception.FeverReportErrorCode;
import final_project.momeasy.domain.fever_report.exception.FeverReportException;
import final_project.momeasy.domain.fever_report.repository.FeverReportRepository;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.temperature_graph.entity.TemperatureGraph;
import final_project.momeasy.domain.temperature_graph.repository.TemperatureGraphRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TemperatureGraphServiceImpl implements TemperatureGraphService {
    private final TemperatureGraphRepository temperatureGraphRepository;
    private final FeverReportRepository feverReportRepository;
    private final AvgTemperature avgTemperature;

    @Override
    public List<TemperatureGraph> createTemperatureRecordGraph(Long recordId, Parent parent, Long childId) {
        FeverReport feverReport = feverReportRepository.findById(recordId).orElseThrow(()->new FeverReportException(FeverReportErrorCode.NOT_FOUND));

        List<TemperatureGraph> temperatureGraphs = new ArrayList<>();
        // 범위 day1
        for(int t = 0 ; t <24 ; t+=3){
            temperatureGraphs.add(buildTemperatureGraph(feverReport, 0, DayRange.DAY1,t, t, childId));
        }
        // 범위 day3
        for (int d = 2; d >= 0; d--) {
            temperatureGraphs.add(buildTemperatureGraph(feverReport, d, DayRange.DAY3,0, 6,childId));   // 새벽
            temperatureGraphs.add(buildTemperatureGraph(feverReport, d, DayRange.DAY3,6, 12,childId));  // 오전
            temperatureGraphs.add(buildTemperatureGraph(feverReport, d, DayRange.DAY3,12, 24,childId)); // 오후
        }
        // 범위 day7
        for(int d = 6; d>=0; d--){
            temperatureGraphs.add(buildTemperatureGraph(feverReport, d, DayRange.DAY7,0, 24,childId));
        }
        temperatureGraphRepository.saveAll(temperatureGraphs);
        return temperatureGraphs;
    }

    private TemperatureGraph buildTemperatureGraph(FeverReport feverReport, int dayOffset, DayRange dayRange , int startHour, int endHour, Long childId) {
        float avg = dayRange == DayRange.DAY1 ? avgTemperature.getTemperatureAvgBy3Hour(startHour, childId) : avgTemperature.getTemperatureAvgByDayAndTimeRange(dayOffset, startHour, endHour, childId);
        TemperatureGraph temperatureGraph = TemperatureGraph.builder()
                .avgTemperature(avg)
                .dayRange(dayRange)
                .build();
        temperatureGraph.setFeverReport(feverReport);
        return temperatureGraph;
    }
}
