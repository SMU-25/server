package final_project.momeasy.domain.fever_record.service;

import final_project.momeasy.domain.fever_record.dto.FeverRecordResponseDTO;

import java.util.List;

public interface FeverRecordQueryService {
    FeverRecordResponseDTO.FeverRecordViewDTO getFeverRecord(Long childId);

    List<FeverRecordResponseDTO.FeverRecordViewDTO> getFeverRecordPage(Long childId, int page);

    List<FeverRecordResponseDTO.FeverRecordViewDTO> getFeverRecordList(Long childId);
}
