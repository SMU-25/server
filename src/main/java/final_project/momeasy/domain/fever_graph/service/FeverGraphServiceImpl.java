package final_project.momeasy.domain.fever_graph.service;

import final_project.momeasy.common.enums.DayRange;
import final_project.momeasy.domain.fever_graph.entity.FeverGraph;
import final_project.momeasy.domain.fever_graph.repository.FeverGraphRepository;

import final_project.momeasy.domain.fever_report.entity.FeverReport;
import final_project.momeasy.domain.fever_report.exception.FeverReportErrorCode;
import final_project.momeasy.domain.fever_report.exception.FeverReportException;
import final_project.momeasy.domain.fever_report.repository.FeverReportRepository;
import final_project.momeasy.domain.parent.entity.Parent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FeverGraphServiceImpl implements FeverGraphService {
    private final FeverGraphRepository feverGraphRepository;
    private final FeverReportRepository feverReportRepository;
    private final AvgFever avgFever;

    @Override
    public List<FeverGraph> createFeverRecordGraph(Long recordId, Parent parent, Long childId) {
        FeverReport feverReport = feverReportRepository.findById(recordId).orElseThrow(()->new FeverReportException(FeverReportErrorCode.NOT_FOUND));

        List<FeverGraph> feverGraphs = new ArrayList<>();
        // 범위 day1
        for(int t = 0 ; t <24 ; t+=3){
            feverGraphs.add(buildFeverGraph(feverReport, 0, DayRange.Day1,t, t, childId));
        }
        // 범위 day3
        for (int d = 2; d >= 0; d--) {
            feverGraphs.add(buildFeverGraph(feverReport, d, DayRange.Day3,0, 6,childId));   // 새벽
            feverGraphs.add(buildFeverGraph(feverReport, d, DayRange.Day3,6, 12,childId));  // 오전
            feverGraphs.add(buildFeverGraph(feverReport, d, DayRange.Day3,12, 24,childId)); // 오후
        }
        // 범위 day7
        for(int d = 6; d>=0; d--){
            feverGraphs.add(buildFeverGraph(feverReport, d, DayRange.Day7,0, 24,childId));
        }
        feverGraphRepository.saveAll(feverGraphs);
        return feverGraphs;
    }

    private FeverGraph buildFeverGraph(FeverReport feverReport, int dayOffset, DayRange dayRange ,int startHour, int endHour, Long childId) {
        float avg = dayRange == DayRange.Day1 ? avgFever.getFeverAvgBy3Hour(startHour, childId) : avgFever.getFeverAvgByDayAndTimeRange(dayOffset, startHour, endHour, childId);
        FeverGraph feverGraph = FeverGraph.builder()
                .fever(avg)
                .dayRange(dayRange)
                .build();
        feverGraph.setFeverReport(feverReport);
        return feverGraph;
    }
}
