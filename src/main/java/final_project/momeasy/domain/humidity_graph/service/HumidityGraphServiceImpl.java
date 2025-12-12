package final_project.momeasy.domain.humidity_graph.service;

import final_project.momeasy.common.enums.DayRange;
import final_project.momeasy.domain.fever_report.entity.FeverReport;
import final_project.momeasy.domain.fever_report.exception.FeverReportErrorCode;
import final_project.momeasy.domain.fever_report.exception.FeverReportException;
import final_project.momeasy.domain.fever_report.repository.FeverReportRepository;
import final_project.momeasy.domain.humidity_graph.dto.HumidityGraphResponseDTO;
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
    private final HumidityGenerator humidityGenerator;

    @Override
    public List<HumidityGraph> createHumidityRecordGraph(Long recordId, Parent parent, Long childId) {
        FeverReport feverReport = feverReportRepository.findById(recordId).orElseThrow(() -> new FeverReportException(FeverReportErrorCode.NOT_FOUND));

        List<HumidityGraph> humidityGraphs = new ArrayList<>();
        // 범위 day1
        for (int t = 0; t < 24; t += 3) {
            humidityGraphs.add(humidityGenerator.HumidityGraph(feverReport, 0, DayRange.DAY1, t, t, childId));
        }
        // 범위 day3
        for (int d = 2; d >= 0; d--) {
            humidityGraphs.add(humidityGenerator.HumidityGraph(feverReport, d, DayRange.DAY3, 0, 6, childId));   // 새벽
            humidityGraphs.add(humidityGenerator.HumidityGraph(feverReport, d, DayRange.DAY3, 6, 12, childId));  // 오전
            humidityGraphs.add(humidityGenerator.HumidityGraph(feverReport, d, DayRange.DAY3, 12, 24, childId)); // 오후
        }
        // 범위 day7
        for (int d = 6; d >= 0; d--) {
            humidityGraphs.add(humidityGenerator.HumidityGraph(feverReport, d, DayRange.DAY7, 0, 24, childId));
        }
        humidityGraphRepositoy.saveAll(humidityGraphs);
        return humidityGraphs;
    }

    @Override
    public List<HumidityGraphResponseDTO.HumidityGraphHomecamViewDTO> getHumidityRecordHomecamGraph(Parent parent, Long childId) {

        List<HumidityGraphResponseDTO.HumidityGraphHomecamViewDTO> humidityGraphs = new ArrayList<>();
        // 범위 day1
        for (int t = 0; t < 24; t += 3) {
            humidityGraphs.add(humidityGenerator.HomecamHumidityGraph(0, DayRange.DAY1, t, t, childId));
        }
        // 범위 day3
        for (int d = 2; d >= 0; d--) {
            humidityGraphs.add(humidityGenerator.HomecamHumidityGraph(d, DayRange.DAY3, 0, 6, childId));   // 새벽
            humidityGraphs.add(humidityGenerator.HomecamHumidityGraph(d, DayRange.DAY3, 6, 12, childId));  // 오전
            humidityGraphs.add(humidityGenerator.HomecamHumidityGraph(d, DayRange.DAY3, 12, 24, childId)); // 오후
        }
        // 범위 day7
        for (int d = 6; d >= 0; d--) {
            humidityGraphs.add(humidityGenerator.HomecamHumidityGraph(d, DayRange.DAY7, 0, 24, childId));
        }
        return humidityGraphs;
    }
}
