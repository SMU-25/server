package final_project.momeasy.domain.fever_record.service;

import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.child.repository.ChildRepository;
import final_project.momeasy.domain.fever_record.entity.FeverRecord;
import final_project.momeasy.domain.fever_record.repository.FeverRecordRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FeverRecordService {
    private final FeverRecordRepository feverRecordRepository;
    private final ChildRepository childRepository;

    // // 아이에게 홈캠이 있는지 예외 처리 필요, for문에 센서로 데이터 입력 받아야함
    @Scheduled(cron = "0 * * * * *")
    public void createFeverRecord(){
        log.info("Starting scheduled create Fever Record");
        List<Child> childList = childRepository.findAll();
        for(Child child : childList){
            FeverRecord feverRecord = FeverRecord.builder()
                    .fever(36.5f)
                    .build();
            feverRecord.setChild(child);
            feverRecordRepository.save(feverRecord);
            log.info(child.getName()+" ("+feverRecord.getId()+") Fever Record created");
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteFeverRecord(){
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        feverRecordRepository.deleteByCreatedAtBefore(sevenDaysAgo);
        log.info("Fever Record deleted");
    }
}
