package final_project.momeasy.domain.temperature_graph.service;

import final_project.momeasy.domain.room_condition.entity.RoomCondition;
import final_project.momeasy.domain.room_condition.repository.RoomConditionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AvgTemperature {
    private final RoomConditionRepository roomConditionRepository;

    public float getTemperatureAvgBy3Hour(int time, Long childId) {
        LocalDateTime start = LocalDate.now().atTime(time,0);
        LocalDateTime end = (time == 21)
                ?LocalDate.now().plusDays(1).atStartOfDay()
                :LocalDate.now().atTime(time+3,0);
        List<RoomCondition> roomConditions = roomConditionRepository.findByChildIdAndCreatedAtBetween(childId,start,end);
        return (float) Math.round(
                roomConditions.stream()
                        .mapToDouble(RoomCondition::getTemperature)
                        .average()
                        .orElse(0.0) * 10
        ) / 10;
    }

    public float getTemperatureAvgByDayAndTimeRange(int day, int time1, int time2, Long childId) {
        LocalDate now = LocalDate.now().minusDays(day);
        LocalDateTime start = now.atTime(time1,0);
        LocalDateTime end = (time2 == 24)
                ? now.plusDays(1).atStartOfDay()
                : now.atTime(time2, 0);
        List<RoomCondition> roomConditions = roomConditionRepository.findByChildIdAndCreatedAtBetween(childId, start,end);
        return (float) Math.round(
                roomConditions.stream()
                        .mapToDouble(RoomCondition::getTemperature)
                        .average()
                        .orElse(0.0) * 10
        ) / 10;
    }

}
