package final_project.momeasy.domain.fever_record.service;

import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.child.exception.ChildErrorCode;
import final_project.momeasy.domain.child.exception.ChildException;
import final_project.momeasy.domain.child.repository.ChildRepository;
import final_project.momeasy.domain.fever_record.converter.FeverRecordConverter;
import final_project.momeasy.domain.fever_record.dto.FeverRecordResponseDTO;
import final_project.momeasy.domain.fever_record.entity.FeverRecord;
import final_project.momeasy.domain.fever_record.exception.FeverRecordErrorCode;
import final_project.momeasy.domain.fever_record.exception.FeverRecordException;
import final_project.momeasy.domain.fever_record.repository.FeverRecordRepository;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.parent.entity.ParentChild;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeverRecordQueryServiceImpl implements FeverRecordQueryService {
    private final FeverRecordRepository feverRecordRepository;
    private final ChildRepository childRepository;

    @Override
    public FeverRecordResponseDTO.FeverRecordViewDTO getFeverRecord(Long childId, Parent parent) {
        childRepository.findById(childId).orElseThrow(() -> new ChildException(ChildErrorCode.NOT_FOUND));
        if(!childRepository.existsByChildIdAndParentId(childId, parent.getId())) {
            throw new FeverRecordException(FeverRecordErrorCode.UNAUTHORIZED_ACCESS);
        }
        FeverRecord feverRecord = feverRecordRepository.findTopByChildIdOrderByCreatedAtDesc(childId).orElseThrow(() -> new FeverRecordException(FeverRecordErrorCode.NOT_FOUND));
        return FeverRecordConverter.toFeverRecordResponseDTO(feverRecord);
    }

    @Override
    public List<FeverRecordResponseDTO.FeverRecordViewDTO> getFeverRecordPage(Long childId, int page, Parent parent) {
        childRepository.findById(childId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        if(!childRepository.existsByChildIdAndParentId(childId, parent.getId())) {
            throw new FeverRecordException(FeverRecordErrorCode.UNAUTHORIZED_ACCESS);
        }
        Pageable pageable = PageRequest.of(page, 10);
        Slice<FeverRecord> feverRecords = feverRecordRepository.findAllByChildIdOrderByCreatedAtDesc(childId,pageable);
        if(feverRecords.isEmpty()){
            throw new FeverRecordException(FeverRecordErrorCode.NOT_FOUND);
        }
        List<FeverRecordResponseDTO.FeverRecordViewDTO> feverRecordsDTO = feverRecords.stream().map(FeverRecordConverter::toFeverRecordResponseDTO).toList();
        return feverRecordsDTO;
    }
}
