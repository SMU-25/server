package final_project.momeasy.domain.home_cam.service;

import final_project.momeasy.common.enums.RecordState;
import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.child.exception.ChildErrorCode;
import final_project.momeasy.domain.child.exception.ChildException;
import final_project.momeasy.domain.child.repository.ChildRepository;
import final_project.momeasy.domain.fever_record.entity.FeverRecord;
import final_project.momeasy.domain.fever_record.repository.FeverRecordBulkRepository;
import final_project.momeasy.domain.home_cam.converter.HomecamConverter;
import final_project.momeasy.domain.home_cam.dto.HomecamRequestDTO;
import final_project.momeasy.domain.home_cam.dto.HomecamResponseDTO;
import final_project.momeasy.domain.home_cam.entity.Homecam;
import final_project.momeasy.domain.home_cam.exception.HomecamErrorCode;
import final_project.momeasy.domain.home_cam.exception.HomecamException;
import final_project.momeasy.domain.home_cam.repository.HomecamRepository;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.room_condition.entity.RoomCondition;
import final_project.momeasy.domain.room_condition.repository.RoomConditionBulkRepository;
import final_project.momeasy.global.tcp.SensorDataQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class HomecamServiceImpl implements HomecamService {
    private final HomecamRepository homecamRepository;
    private final ChildRepository childRepository;
    private final FeverRecordBulkRepository feverRecordBulkRepository;
    private final RoomConditionBulkRepository roomConditionBulkRepository;
    private final SensorDataQueue sensorDataQueue;

    @Scheduled(cron = "*/30 * * * * *")
    public void createSensorData(){
        log.info("Starting scheduled create Sensor Data");
        List<String> batch = new ArrayList<>();
        sensorDataQueue.queue.drainTo(batch);
        if(batch.isEmpty()){
            log.info("Non Sensor Data");
            return;
        }
        List<FeverRecord> feverRecords = new ArrayList<>();
        List<RoomCondition> roomConditions = new ArrayList<>();

        for(String line : batch){
            String[] parts = line.split("::");
            if(parts.length < 2){
                log.warn("error data format: {}", line);
                continue;
            }
            String boardId = parts[0];
            if(parts[1].equals("ESP32CAM")){
                Homecam homecam = homecamRepository.findBySerialNum(boardId).orElseThrow(()->new HomecamException(HomecamErrorCode.NOT_FOUND));
                homecam.setVideo_url(parts[2]);
                continue;
            }

            Homecam homecam = homecamRepository.findBySerialNum(boardId).orElseThrow(()->new HomecamException(HomecamErrorCode.NOT_FOUND));
            Child child = childRepository.findByHomecam(homecam).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));

            try{
                String[] env = parts[1].split(",");
                if(env.length>=2) {
                    float humidity = Float.parseFloat(env[0]);
                    float temperature = Float.parseFloat(env[1]);
                    RoomCondition roomCondition = RoomCondition.builder()
                            .temperature(temperature)
                            .humidity(humidity)
                            .child(child)
                            .build();
                    roomConditions.add(roomCondition);
                }
            } catch(Exception e){
                log.warn("error data format: {}", line);
            }

            if(parts.length>=3 && parts[2]!=null && !parts[2].isBlank()){
                try {
                    double sum = 0.0;
                    int count = 0;
                    String[] tempValues = parts[2].split(",");
                    for (String val : tempValues) {
                        sum += Float.parseFloat(val);
                        count++;
                    }
                    if (count > 0) {
                        float avgFever = (float) (count > 0 ? sum / count : 0.0);
                        avgFever = Math.round(avgFever * 10) / 10.0f;
                        avgFever+=5;
                        RecordState state = (avgFever < 35.0 || avgFever > 41.0)
                                ? RecordState.NOT_HUMAN
                                : RecordState.HUMAN;
                        log.info("fever: {}, RecordState: {}", avgFever, state);
                        FeverRecord feverRecord = FeverRecord.builder()
                                .fever(avgFever)
                                .child(child)
                                .recordState(state)
                                .build();
                        feverRecords.add(feverRecord);
                    }
                }catch(Exception e){
                    log.warn("FeverRecord parse error: {}",line);
                }
            }
        }
        feverRecordBulkRepository.saveAll(feverRecords);
        roomConditionBulkRepository.saveAll(roomConditions);
        log.info("Ending scheduled create Sensor Data");
    }

    @Override
    public void deleteHomecam(Long homecamId,Parent parent) {
        Homecam homecam = homecamRepository.findById(homecamId).orElseThrow(()->new HomecamException(HomecamErrorCode.NOT_FOUND));
        if(!homecam.getParent().equals(parent)) {
            throw new HomecamException(HomecamErrorCode.UNAUTHORIZED_ACCESS);
        }
        homecamRepository.delete(homecam);
    }

    @Override
    public HomecamResponseDTO.HomecamDTO createHomecam(HomecamRequestDTO.HomecamCreateDTO homecamRequestDTO, Parent parent) {
        Long childId = homecamRequestDTO.getChildId();
        Child child = childRepository.findById(childId).orElseThrow(() -> new ChildException(ChildErrorCode.NOT_FOUND));
        if (!childRepository.existsByChildIdAndParentId(childId, parent.getId())) {
            throw new ChildException(ChildErrorCode.UNAUTHORIZED_ACCESS);
        }
        homecamRepository.findByChildId(childId).ifPresent(homecam -> {throw new HomecamException(HomecamErrorCode.ALREADY_HAVE);});
        Homecam homecam = HomecamConverter.toHomecam(homecamRequestDTO,parent);
        homecam.setParent(parent);
        homecam.setChild(child);
        homecamRepository.save(homecam);
        return HomecamConverter.toHomecamDTO(homecam);
    }

    @Override
    public void updateHomecam(HomecamRequestDTO.HomecamUpdateDTO homecamUpdateDTO, Parent parent, Long homecamId) {
        Long childId = homecamUpdateDTO.getChildId();
        Homecam homecam = homecamRepository.findById(homecamId).orElseThrow(()->new HomecamException(HomecamErrorCode.NOT_FOUND));
        Child child = childRepository.findById(childId).orElseThrow(() -> new ChildException(ChildErrorCode.NOT_FOUND));
        if(!homecam.getParent().equals(parent)) {
            throw new HomecamException(HomecamErrorCode.UNAUTHORIZED_ACCESS);
        }
        if(!childRepository.existsByChildIdAndParentId(childId, parent.getId())){
            throw new ChildException(ChildErrorCode.UNAUTHORIZED_ACCESS);
        }
        homecam.update(homecamUpdateDTO);
        homecam.setChild(child);
    }
}
