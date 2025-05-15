package final_project.momeasy.domain.fever_record.service;

import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.child.repository.ChildRepository;
import final_project.momeasy.domain.fever_record.converter.FeverRecordConverter;
import final_project.momeasy.domain.fever_record.dto.FeverRecordRequestDTO;
import final_project.momeasy.domain.fever_record.dto.FeverRecordResponseDTO;
import final_project.momeasy.domain.fever_record.entity.FeverRecord;
import final_project.momeasy.domain.fever_record.exception.FeverRecordErrorCode;
import final_project.momeasy.domain.fever_record.exception.FeverRecordException;
import final_project.momeasy.domain.fever_record.repository.FeverRecordRepository;
import final_project.momeasy.domain.parent.repository.ParentRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FeverRecordQueryServiceImpl implements FeverRecordQueryService {
    private final FeverRecordRepository feverRecordRepository;
    private final ChildRepository childRepository;

    // 아이에게 홈캠이 있는지 예외 처리 필요
    @Override
    public FeverRecordResponseDTO.FeverRecordViewDTO getFeverRecord(Long childId) {
        FeverRecord feverRecord = feverRecordRepository.findTopByChildIdOrderByIdDesc(childId).orElseThrow(()->new FeverRecordException(FeverRecordErrorCode.NOT_FOUND));
        if(feverRecord.getChild()!= childRepository.findById(childId).get()) {
            throw new FeverRecordException(FeverRecordErrorCode.NOT_FOUND);
        }
        return FeverRecordConverter.toFeverRecordResponseDTO(feverRecord);
    }

    @Override
    public List<FeverRecordResponseDTO.FeverRecordViewDTO> getFeverRecordPage(Long childId, int page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Slice<FeverRecord> feverRecords = feverRecordRepository.findAllByChildIdOrderByIdDesc(childId,pageRequest);
        List<FeverRecordResponseDTO.FeverRecordViewDTO> feverRecordsDTO = feverRecords.stream().map(FeverRecordConverter::toFeverRecordResponseDTO).toList();
        return feverRecordsDTO;
    }

    @Override
    public List<FeverRecordResponseDTO.FeverRecordViewDTO> getFeverRecordList(Long childId) {
        List<FeverRecord> feverRecords = feverRecordRepository.findAllByChildId(childId);
        List<FeverRecordResponseDTO.FeverRecordViewDTO> feverRecordsDTO = feverRecords.stream().map(FeverRecordConverter::toFeverRecordResponseDTO).toList();
        return feverRecordsDTO;
    }
}
