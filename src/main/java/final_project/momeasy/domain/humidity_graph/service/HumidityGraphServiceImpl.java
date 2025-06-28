package final_project.momeasy.domain.humidity_graph.service;

import final_project.momeasy.common.enums.DayRange;
import final_project.momeasy.domain.fever_report.entity.FeverReport;
import final_project.momeasy.domain.fever_report.exception.FeverReportErrorCode;
import final_project.momeasy.domain.fever_report.exception.FeverReportException;
import final_project.momeasy.domain.fever_report.repository.FeverReportRepository;
import final_project.momeasy.domain.humidity_graph.entity.HumidityGraph;
import final_project.momeasy.domain.humidity_graph.repository.HumidityGraphRepositoy;
import final_project.momeasy.domain.parent.entity.Parent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class HumidityGraphServiceImpl implements HumidityGraphService {
    private final HumidityGraphRepositoy humidityGraphRepositoy;
    private final FeverReportRepository feverReportRepository;
    private final AvgHumidity avgHumidity;

    @Override
    public List<HumidityGraph> createHumidityRecordGraph(Long recordId, Parent parent, Long childId) {
        FeverReport feverReport = feverReportRepository.findById(recordId).orElseThrow(()->new FeverReportException(FeverReportErrorCode.NOT_FOUND));

        List<HumidityGraph> humidityGraphs = new ArrayList<>();
        // 범위 day1
        for(int t = 0 ; t <24 ; t+=3){
            humidityGraphs.add(buildHumidityGraph(feverReport, 0, DayRange.Day1,t, t, childId));
        }
        // 범위 day3
        for (int d = 2; d >= 0; d--) {
            humidityGraphs.add(buildHumidityGraph(feverReport, d, DayRange.Day3,0, 6,childId));   // 새벽
            humidityGraphs.add(buildHumidityGraph(feverReport, d, DayRange.Day3,6, 12,childId));  // 오전
            humidityGraphs.add(buildHumidityGraph(feverReport, d, DayRange.Day3,12, 24,childId)); // 오후
        }
        // 범위 day7
        for(int d = 6; d>=0; d--){
            humidityGraphs.add(buildHumidityGraph(feverReport, d, DayRange.Day7,0, 24,childId));
        }
        humidityGraphRepositoy.saveAll(humidityGraphs);
        return humidityGraphs;
    }

    private HumidityGraph buildHumidityGraph(FeverReport feverReport, int dayOffset, DayRange dayRange , int startHour, int endHour, Long childId) {
        float avg = dayRange == DayRange.Day1 ? avgHumidity.getHumidityAvgBy3Hour(startHour, childId) : avgHumidity.getHumidityAvgByDayAndTimeRange(dayOffset, startHour, endHour, childId);
        HumidityGraph humidityGraph = HumidityGraph.builder()
                .humidity(avg)
                .dayRange(dayRange)
                .build();
        humidityGraph.setFeverReport(feverReport);
        return humidityGraph;
    }
}
