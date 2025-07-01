package final_project.momeasy.domain.fever_record.service;

import final_project.momeasy.domain.fever_record.dto.FeverRecordResponseDTO;
import final_project.momeasy.domain.parent.entity.Parent;

import java.util.List;

public interface FeverRecordQueryService {
    FeverRecordResponseDTO.FeverRecordViewDTO getFeverRecord(Long childId, Parent parent);

    FeverRecordResponseDTO.FeverRecordViewDTO getRecentHighFeverRecord(Long childId, Parent parent);

    List<FeverRecordResponseDTO.FeverRecordViewDTO> getFeverRecordPage(Long childId, int page, Parent parent);

    List<FeverRecordResponseDTO.FeverRecordGraphDTO> getFeverRecordGraphDay1(Long childId, Parent parent);

    List<FeverRecordResponseDTO.FeverRecordGraphDTO> getFeverRecordGraphDay3(Long childId, Parent parent);

    List<FeverRecordResponseDTO.FeverRecordGraphDTO> getFeverRecordGraphDay7(Long childId, Parent parent);
}
