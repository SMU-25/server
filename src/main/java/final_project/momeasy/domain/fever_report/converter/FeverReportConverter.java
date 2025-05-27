package final_project.momeasy.domain.fever_report.converter;


import final_project.momeasy.common.enums.SymptomType;
import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.fever_report.dto.FeverReportRequestDTO;
import final_project.momeasy.domain.fever_report.dto.FeverReportResponseDTO;
import final_project.momeasy.domain.fever_report.entity.FeverReport;
import final_project.momeasy.domain.fever_report.entity.FeverReportSymptom;
import final_project.momeasy.domain.symptom.entity.Symptom;

import java.util.List;

public class FeverReportConverter {
    public static FeverReportResponseDTO.FeverReportViewDTO toFeverReportViewDTO(FeverReport feverReport) {
        return FeverReportResponseDTO.FeverReportViewDTO.builder()
                .outing(feverReport.getOuting())
                .special(feverReport.getSpecial())
                .etc_symptom(feverReport.getEtc_symptom())
                .symptoms(feverReport.getFeverReportSymptoms().stream().map(FeverReportSymptom -> FeverReportSymptom.getSymptom().getSymptom()).toList())
                .build();
    }

    public static FeverReport toFeverReport(FeverReportRequestDTO.FeverReportCreateDTO feverReportCreateDTO, String special) {
        return FeverReport.builder()
                .outing(feverReportCreateDTO.getOuting())
                .special(special)
                .build();
    }
}
