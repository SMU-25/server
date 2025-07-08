package final_project.momeasy.domain.fever_record.service;

import final_project.momeasy.domain.fever_record.repository.FeverRecordRepository;
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
public class FeverRecordService {
    private final FeverRecordRepository feverRecordRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteFeverRecord(){
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        feverRecordRepository.deleteByCreatedAtBefore(sevenDaysAgo);
        log.info("Fever Record deleted");
    }
}
