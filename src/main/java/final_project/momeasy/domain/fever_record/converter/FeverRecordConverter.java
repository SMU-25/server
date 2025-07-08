package final_project.momeasy.domain.fever_record.converter;

import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.fever_record.dto.FeverRecordRequestDTO;
import final_project.momeasy.domain.fever_record.dto.FeverRecordResponseDTO;
import final_project.momeasy.domain.fever_record.entity.FeverRecord;

import java.util.List;

public class FeverRecordConverter {
    public static FeverRecordResponseDTO.FeverRecordViewDTO toFeverRecordResponseDTO(FeverRecord feverRecord) {
        return FeverRecordResponseDTO.FeverRecordViewDTO.builder()
                .fever(feverRecord.getFever())
                .createdAt(feverRecord.getCreatedAt())
                .build();
    }

    public static FeverRecordResponseDTO.FeverRecordListViewDTO toFeverRecordListViewDTO(List<FeverRecordResponseDTO.FeverRecordViewDTO> feverRecords,
         Boolean hasNext, Long cursor) {
        return FeverRecordResponseDTO.FeverRecordListViewDTO.builder()
                .hasNext(hasNext)
                .cursor(cursor)
                .feverRecords(feverRecords)
                .build();
    }
}
