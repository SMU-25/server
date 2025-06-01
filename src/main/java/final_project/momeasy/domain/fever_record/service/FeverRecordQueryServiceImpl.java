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
import final_project.momeasy.domain.parent.exception.ParentErrorCode;
import final_project.momeasy.domain.parent.exception.ParentException;
import final_project.momeasy.domain.parent.repository.ParentRepository;
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
    private final ParentRepository parentRepository;

    @Override
    public FeverRecordResponseDTO.FeverRecordViewDTO getFeverRecord(Long childId, Parent parent) {
        Parent findparent = parentRepository.findById(parent.getId()).orElseThrow(()->new ParentException(ParentErrorCode.NOT_FOUND));
        Child child = childRepository.findById(childId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        List<ParentChild> parentChildren = child.getParentChildren();
        FeverRecord feverRecord = null;
        for(ParentChild parentChild : parentChildren) {
            if(parentChild.getParent().getId().equals(findparent.getId()))
            feverRecord = feverRecordRepository.findTopByChildIdOrderByIdDesc(childId).orElseThrow(()->new FeverRecordException(FeverRecordErrorCode.NOT_FOUND));
            if(feverRecord.getChild()!= child) {
                throw new FeverRecordException(FeverRecordErrorCode.UNAUTHORIZED_ACCESS);
            }
        }
        if(feverRecord == null) {
            throw new FeverRecordException(FeverRecordErrorCode.UNAUTHORIZED_ACCESS);
        }
        return FeverRecordConverter.toFeverRecordResponseDTO(feverRecord);
    }

    @Override
    public List<FeverRecordResponseDTO.FeverRecordViewDTO> getFeverRecordPage(Long childId, int page, Parent parent) {
        Parent findparent = parentRepository.findById(parent.getId()).orElseThrow(()->new ParentException(ParentErrorCode.NOT_FOUND));
        Child child = childRepository.findById(childId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        Pageable pageable = PageRequest.of(page, 10);
        Slice<FeverRecord> feverRecords = feverRecordRepository.findAllByChildIdOrderByIdDesc(childId,pageable);
        for(FeverRecord feverRecord : feverRecords) {
            if(feverRecord.getChild()!= child) {
                throw new FeverRecordException(FeverRecordErrorCode.UNAUTHORIZED_ACCESS);
            }
        }
        List<FeverRecordResponseDTO.FeverRecordViewDTO> feverRecordsDTO = feverRecords.stream().map(FeverRecordConverter::toFeverRecordResponseDTO).toList();
        return feverRecordsDTO;
    }

    @Override
    public List<FeverRecordResponseDTO.FeverRecordViewDTO> getFeverRecordList(Long childId, Parent parent) {
        Parent findparent = parentRepository.findById(parent.getId()).orElseThrow(()->new ParentException(ParentErrorCode.NOT_FOUND));
        Child child = childRepository.findById(childId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        List<FeverRecord> feverRecords = feverRecordRepository.findAllByChildId(childId);
        for(FeverRecord feverRecord : feverRecords) {
            if(feverRecord.getChild()!= child) {
                throw new FeverRecordException(FeverRecordErrorCode.UNAUTHORIZED_ACCESS);
            }
        }
        List<FeverRecordResponseDTO.FeverRecordViewDTO> feverRecordsDTO = feverRecords.stream().map(FeverRecordConverter::toFeverRecordResponseDTO).toList();
        return feverRecordsDTO;
    }
}
