package final_project.momeasy.domain.fever_record.service;

import final_project.momeasy.domain.child.exception.ChildErrorCode;
import final_project.momeasy.domain.child.exception.ChildException;
import final_project.momeasy.domain.child.repository.ChildRepository;
import final_project.momeasy.domain.fever_graph.service.AvgFever;
import final_project.momeasy.domain.fever_record.converter.FeverRecordConverter;
import final_project.momeasy.domain.fever_record.dto.FeverRecordResponseDTO;
import final_project.momeasy.domain.fever_record.entity.FeverRecord;
import final_project.momeasy.domain.fever_record.exception.FeverRecordErrorCode;
import final_project.momeasy.domain.fever_record.exception.FeverRecordException;
import final_project.momeasy.domain.fever_record.repository.FeverRecordRepository;
import final_project.momeasy.domain.parent.entity.Parent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeverRecordQueryServiceImpl implements FeverRecordQueryService {
    private final FeverRecordRepository feverRecordRepository;
    private final ChildRepository childRepository;
    private final AvgFever avgFever;

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
    public FeverRecordResponseDTO.FeverRecordViewDTO getRecentHighFeverRecord(Long childId, Parent parent) {
        childRepository.findById(childId).orElseThrow(() -> new ChildException(ChildErrorCode.NOT_FOUND));
        if(!childRepository.existsByChildIdAndParentId(childId, parent.getId())) {
            throw new FeverRecordException(FeverRecordErrorCode.UNAUTHORIZED_ACCESS);
        }
        FeverRecord feverRecord = feverRecordRepository.findRecentFeverRecord(childId).orElseThrow(() -> new FeverRecordException(FeverRecordErrorCode.NOT_FOUND));
        return FeverRecordConverter.toFeverRecordResponseDTO(feverRecord);
    }

    @Override
    public FeverRecordResponseDTO.FeverRecordListViewDTO getFeverRecordPage(Long childId, Long cursor, Integer size, Parent parent) {
        childRepository.findById(childId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        if(!childRepository.existsByChildIdAndParentId(childId, parent.getId())) {
            throw new FeverRecordException(FeverRecordErrorCode.UNAUTHORIZED_ACCESS);
        }
        Pageable pageable = PageRequest.of(0,size);
        Slice<FeverRecord> feverRecords = feverRecordRepository.findFeverRecordCursorPagination(childId,cursor,pageable);
        List<FeverRecord> feverRecordList = feverRecords.toList();
        List<FeverRecordResponseDTO.FeverRecordViewDTO> feverRecordViewDTOList = feverRecordList.stream().map(FeverRecordConverter::toFeverRecordResponseDTO).toList();

        if(feverRecords.isEmpty()) {
            throw new FeverRecordException(FeverRecordErrorCode.NOT_FOUND);
        }

        Long nextCursor = null;
        if(!feverRecordList.isEmpty() && feverRecords.hasNext()) {
            nextCursor = feverRecordList.get(feverRecordList.size()-1).getId();
        }

        return FeverRecordConverter.toFeverRecordListViewDTO(feverRecordViewDTOList,feverRecords.hasNext(), nextCursor);
    }

    @Override
    public List<FeverRecordResponseDTO.FeverRecordGraphDTO> getFeverRecordGraphDay1(Long childId, Parent parent) {
        childRepository.findById(childId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        if(!childRepository.existsByChildIdAndParentId(childId, parent.getId())) {
            throw new FeverRecordException(FeverRecordErrorCode.UNAUTHORIZED_ACCESS);
        }
        List<FeverRecordResponseDTO.FeverRecordGraphDTO> result = new ArrayList<>();
        for(int t = 0 ; t <24 ; t+=3){
            result.add(FeverRecordResponseDTO.FeverRecordGraphDTO.builder()
                        .avgfever(avgFever.getFeverAvgBy3Hour(t, childId))
                        .build());
        }
        return result;
    }

    @Override
    public List<FeverRecordResponseDTO.FeverRecordGraphDTO> getFeverRecordGraphDay3(Long childId, Parent parent) {
        childRepository.findById(childId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        if(!childRepository.existsByChildIdAndParentId(childId, parent.getId())) {
            throw new FeverRecordException(FeverRecordErrorCode.UNAUTHORIZED_ACCESS);
        }
        List<FeverRecordResponseDTO.FeverRecordGraphDTO> result = new ArrayList<>();

        for(int d=2; d>=0; d--) {
            // 새벽: 00:00 ~ 06:00
            result.add(FeverRecordResponseDTO.FeverRecordGraphDTO.builder()
                    .avgfever(avgFever.getFeverAvgByDayAndTimeRange(d,0, 6, childId))
                    .build());

            // 오전: 06:00 ~ 12:00
            result.add(FeverRecordResponseDTO.FeverRecordGraphDTO.builder()
                    .avgfever(avgFever.getFeverAvgByDayAndTimeRange(d,6, 12, childId))
                    .build());

            // 오후: 12:00 ~ 24:00
            result.add(FeverRecordResponseDTO.FeverRecordGraphDTO.builder()
                    .avgfever(avgFever.getFeverAvgByDayAndTimeRange(d,12, 24,childId))
                    .build());
        }
        return result;
    }

    @Override
    public List<FeverRecordResponseDTO.FeverRecordGraphDTO> getFeverRecordGraphDay7(Long childId, Parent parent) {
        childRepository.findById(childId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        if(!childRepository.existsByChildIdAndParentId(childId, parent.getId())) {
            throw new FeverRecordException(FeverRecordErrorCode.UNAUTHORIZED_ACCESS);
        }
        List<FeverRecordResponseDTO.FeverRecordGraphDTO> result = new ArrayList<>();
        for(int d = 6; d>=0; d--){
            result.add(FeverRecordResponseDTO.FeverRecordGraphDTO.builder()
                    .avgfever(avgFever.getFeverAvgByDayAndTimeRange(d,0,24,childId))
                    .build());
        }
        return result;
    }

}
