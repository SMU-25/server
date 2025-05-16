package final_project.momeasy.domain.room_condition.service;

import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.child.repository.ChildRepository;
import final_project.momeasy.domain.room_condition.entity.RoomCondition;
import final_project.momeasy.domain.room_condition.repository.RoomConditionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class RoomConditionService {
    private final RoomConditionRepository roomConditionRepository;
    private final ChildRepository childRepository;

    // // 아이에게 홈캠이 있는지 예외 처리 필요, for문에 센서로 데이터 입력 받아야함
    @Scheduled(cron = "0 * * * * *")
    public void createRoomCondition() {
        log.info("Starting scheduled create Room Condition");
        List<Child> childList = childRepository.findAll();
        for(Child child : childList){
            RoomCondition roomCondition = RoomCondition.builder()
                    .temperature(36.5f)
                    .humidity(50.0f)
                    .build();
            roomCondition.setChild(child);
            roomConditionRepository.save(roomCondition);
            log.info(child.getName()+" ("+roomCondition.getId()+") Room Condition created");
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteRoomCondition(){
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        roomConditionRepository.deleteByCreatedAtBefore(sevenDaysAgo);
        log.info("Room Condition deleted");
    }
}
