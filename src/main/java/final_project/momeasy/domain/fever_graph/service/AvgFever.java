package final_project.momeasy.domain.fever_graph.service;

import final_project.momeasy.domain.fever_record.entity.FeverRecord;
import final_project.momeasy.domain.fever_record.repository.FeverRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class AvgFever {
    private final FeverRecordRepository feverRecordRepository;

    public float getFeverAvgBy3Hour(int t, Long childId) {
        LocalDateTime start = LocalDate.now().atTime(t,0);
        LocalDateTime end = (t == 21)
                ?LocalDate.now().plusDays(1).atStartOfDay()
                :LocalDate.now().atTime(t+3,0);
        List<FeverRecord> feverRecords = feverRecordRepository.findByChildIdAndCreatedAtBetween(childId,start,end);
        return (float) Math.round(
                feverRecords.stream()
                        .mapToDouble(FeverRecord::getFever)
                        .average()
                        .orElse(0.0) * 10
        ) / 10;
    }

    public float getFeverAvgByDayAndTimeRange(int day, int t1, int t2, Long childId) {
        LocalDate now = LocalDate.now().minusDays(day);
        LocalDateTime start = now.atTime(t1,0);
        LocalDateTime end = (t2 == 24)
                ? now.plusDays(1).atStartOfDay()
                : now.atTime(t2, 0);
        List<FeverRecord> feverRecords = feverRecordRepository.findByChildIdAndCreatedAtBetween(childId, start,end);
        return (float) Math.round(
                feverRecords.stream()
                        .mapToDouble(FeverRecord::getFever)
                        .average()
                        .orElse(0.0) * 10
        ) / 10;
    }
}
