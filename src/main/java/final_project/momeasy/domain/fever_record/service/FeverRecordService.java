package final_project.momeasy.domain.fever_record.service;

import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.child.repository.ChildRepository;
import final_project.momeasy.domain.fever_record.entity.FeverRecord;
import final_project.momeasy.domain.fever_record.repository.FeverRecordRepository;
import final_project.momeasy.domain.home_cam.repository.HomecamRepository;
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
    private final HomecamRepository homecamRepository;

    // for문에 센서로 데이터 입력 받아야함
    @Scheduled(cron = "0 * * * * *")
    public void createFeverRecord(){
        log.info("Starting scheduled create Fever Record");
        List<Child> childList = childRepository.findAll();
        for(Child child : childList){
            if(child.getHomecam()==null){continue;}
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
