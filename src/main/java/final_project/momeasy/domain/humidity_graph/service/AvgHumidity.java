package final_project.momeasy.domain.humidity_graph.service;

import final_project.momeasy.domain.room_condition.entity.RoomCondition;
import final_project.momeasy.domain.room_condition.repository.RoomConditionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class AvgHumidity {
    private final RoomConditionRepository roomConditionRepository;

    public float getHumidityAvgBy3Hour(int t, Long childId) {
        LocalDateTime start = LocalDate.now().atTime(t,0);
        LocalDateTime end = (t == 21)
                ?LocalDate.now().plusDays(1).atStartOfDay()
                :LocalDate.now().atTime(t+3,0);
        List<RoomCondition> roomConditions = roomConditionRepository.findByChildIdAndCreatedAtBetween(childId,start,end);
        return (float) Math.round(
                roomConditions.stream()
                        .mapToDouble(RoomCondition::getHumidity)
                        .average()
                        .orElse(0.0) * 10
        ) / 10;
    }

    public float getHumidityAvgByDayAndTimeRange(int day, int t1, int t2, Long childId) {
        LocalDate now = LocalDate.now().minusDays(day);
        LocalDateTime start = now.atTime(t1,0);
        LocalDateTime end = (t2 == 24)
                ? now.plusDays(1).atStartOfDay()
                : now.atTime(t2, 0);
        List<RoomCondition> roomConditions = roomConditionRepository.findByChildIdAndCreatedAtBetween(childId, start,end);
        return (float) Math.round(
                roomConditions.stream()
                        .mapToDouble(RoomCondition::getHumidity)
                        .average()
                        .orElse(0.0) * 10
        ) / 10;
    }

}
