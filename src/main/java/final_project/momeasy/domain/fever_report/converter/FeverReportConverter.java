package final_project.momeasy.domain.fever_report.converter;


import final_project.momeasy.domain.fever_report.dto.FeverReportRequestDTO;
import final_project.momeasy.domain.fever_report.dto.FeverReportResponseDTO;
import final_project.momeasy.domain.fever_report.entity.FeverReport;


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
