package final_project.momeasy.domain.fever_record.service;

import final_project.momeasy.common.enums.RecordState;
import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.child.exception.ChildErrorCode;
import final_project.momeasy.domain.child.exception.ChildException;
import final_project.momeasy.domain.child.repository.ChildRepository;
import final_project.momeasy.domain.fever_record.entity.FeverRecord;
import final_project.momeasy.domain.fever_record.repository.FeverRecordRepository;
import final_project.momeasy.domain.notification.service.NotificationServiceImpl;
import final_project.momeasy.domain.parent.entity.Parent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeverRecordCommandService {

    private final FeverRecordRepository feverRecordRepository;
    private final NotificationServiceImpl notificationService;
    private final ChildRepository childRepository;

    @Transactional
    public void saveFeverRecord(Long childId, Parent parent, Float temperature) {
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new ChildException(ChildErrorCode.NOT_FOUND));

        FeverRecord record = FeverRecord.builder()
                .child(child)
                .fever(temperature)
                .recordState(RecordState.HUMAN)
                .build();

        feverRecordRepository.save(record);

        if (temperature >= 38.0f) {
            String msg = child.getName() + " 현재 체온은 " + String.format("%.1f", temperature) + "도, 고열입니다. 즉시 확인해주세요";

            notificationService.createCareNotification(
                    parent,
                    child,
                    msg,
                    temperature,
                    null,
                    null
            );
        }
    }
}
