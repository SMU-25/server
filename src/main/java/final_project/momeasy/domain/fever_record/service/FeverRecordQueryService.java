package final_project.momeasy.domain.fever_record.service;

import final_project.momeasy.domain.fever_record.dto.FeverRecordResponseDTO;
import final_project.momeasy.domain.parent.entity.Parent;

import java.util.List;

public interface FeverRecordQueryService {
    FeverRecordResponseDTO.FeverRecordStateViewDTO getFeverRecord(Long childId, Parent parent);

    FeverRecordResponseDTO.FeverRecordViewDTO getRecentHighFeverRecord(Long childId, Parent parent);

    FeverRecordResponseDTO.FeverRecordListViewDTO getFeverRecordPage(Long childId, Long cursor, Integer size, Parent parent);

    List<FeverRecordResponseDTO.FeverRecordGraphDTO> getFeverRecordGraphDay1(Long childId, Parent parent);

    List<FeverRecordResponseDTO.FeverRecordGraphDTO> getFeverRecordGraphDay3(Long childId, Parent parent);

    List<FeverRecordResponseDTO.FeverRecordGraphDTO> getFeverRecordGraphDay7(Long childId, Parent parent);
}
