package final_project.momeasy.domain.room_condition.service;


import final_project.momeasy.domain.room_condition.repository.RoomConditionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class RoomConditionService {
    private final RoomConditionRepository roomConditionRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteRoomCondition(){
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        roomConditionRepository.deleteByCreatedAtBefore(sevenDaysAgo);
        log.info("Room Condition deleted");
    }
}
