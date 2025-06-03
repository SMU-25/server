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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeverRecordQueryServiceImpl implements FeverRecordQueryService {
    private final FeverRecordRepository feverRecordRepository;
    private final ChildRepository childRepository;

    @Override
    public FeverRecordResponseDTO.FeverRecordViewDTO getFeverRecord(Long childId, Parent parent) {
        Child child = childRepository.findById(childId).orElseThrow(() -> new ChildException(ChildErrorCode.NOT_FOUND));
        List<ParentChild> parentChildren = child.getParentChildren();
        FeverRecord feverRecord = null;
        for (ParentChild parentChild : parentChildren) {
            if (parentChild.getParent().equals(parent)) {
                feverRecord = feverRecordRepository.findTopByChildIdOrderByIdDesc(childId).orElseThrow(() -> new FeverRecordException(FeverRecordErrorCode.NOT_FOUND));
            }
        }
        if (feverRecord == null) {
            throw new FeverRecordException(FeverRecordErrorCode.UNAUTHORIZED_ACCESS);
        }
        return FeverRecordConverter.toFeverRecordResponseDTO(feverRecord);
    }

    @Override
    public List<FeverRecordResponseDTO.FeverRecordViewDTO> getFeverRecordPage(Long childId, int page, Parent parent) {
        Child child = childRepository.findById(childId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        List<ParentChild> parentChildren = child.getParentChildren();
        Slice<FeverRecord> feverRecords = null;
        for(ParentChild parentChild : parentChildren) {
            if (parentChild.getParent().equals(parent)) {
                Pageable pageable = PageRequest.of(page, 10);
                feverRecords = feverRecordRepository.findAllByChildIdOrderByIdDesc(childId,pageable);
            }
        }
        if(feverRecords == null) {
            throw new FeverRecordException(FeverRecordErrorCode.UNAUTHORIZED_ACCESS);
        }
        if(feverRecords.isEmpty()){
            throw new FeverRecordException(FeverRecordErrorCode.NOT_FOUND);
        }
        List<FeverRecordResponseDTO.FeverRecordViewDTO> feverRecordsDTO = feverRecords.stream().map(FeverRecordConverter::toFeverRecordResponseDTO).toList();
        return feverRecordsDTO;
    }

    @Override
    public List<FeverRecordResponseDTO.FeverRecordViewDTO> getFeverRecordList(Long childId, Parent parent) {
        Child child = childRepository.findById(childId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        List<ParentChild> parentChildren = child.getParentChildren();
        List<FeverRecord> feverRecords = null;
        for(ParentChild parentChild : parentChildren) {
            if (parentChild.getParent().equals(parent)) {
                feverRecords = feverRecordRepository.findAllByChildIdOrderByIdDesc(childId);
            }
        }
        if(feverRecords == null) {
            throw new FeverRecordException(FeverRecordErrorCode.UNAUTHORIZED_ACCESS);
        }
        if(feverRecords.isEmpty()){
            throw new FeverRecordException(FeverRecordErrorCode.NOT_FOUND);
        }
        List<FeverRecordResponseDTO.FeverRecordViewDTO> feverRecordsDTO = feverRecords.stream().map(FeverRecordConverter::toFeverRecordResponseDTO).toList();
        return feverRecordsDTO;
    }
}
