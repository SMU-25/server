package final_project.momeasy.domain.fever_record.service;

import final_project.momeasy.domain.fever_record.dto.FeverRecordResponseDTO;
import final_project.momeasy.domain.parent.entity.Parent;

import java.util.List;

public interface FeverRecordQueryService {
    FeverRecordResponseDTO.FeverRecordViewDTO getFeverRecord(Long childId, Parent parent);

    List<FeverRecordResponseDTO.FeverRecordViewDTO> getFeverRecordPage(Long childId, int page, Parent parent);
}
