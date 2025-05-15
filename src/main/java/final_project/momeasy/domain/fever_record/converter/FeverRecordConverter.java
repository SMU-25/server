package final_project.momeasy.domain.fever_record.converter;

import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.fever_record.dto.FeverRecordRequestDTO;
import final_project.momeasy.domain.fever_record.dto.FeverRecordResponseDTO;
import final_project.momeasy.domain.fever_record.entity.FeverRecord;

public class FeverRecordConverter {
    public static FeverRecord toFeverRecord(FeverRecordRequestDTO.FeverRecordCreateDTO feverRecordCreateDTO, Child child) {
        return FeverRecord.builder()
                .fever(feverRecordCreateDTO.getFever())
                .child(child)
                .build();
    }

    public static FeverRecordResponseDTO.FeverRecordViewDTO toFeverRecordResponseDTO(FeverRecord feverRecord) {
        return FeverRecordResponseDTO.FeverRecordViewDTO.builder()
                .fever(feverRecord.getFever())
                .build();
    }
}
