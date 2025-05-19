package final_project.momeasy.domain.fever_report.converter;


import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.fever_report.dto.FeverReportRequestDTO;
import final_project.momeasy.domain.fever_report.dto.FeverReportResponseDTO;
import final_project.momeasy.domain.fever_report.entity.FeverReport;
import final_project.momeasy.domain.fever_report.entity.FeverReportSymptom;

public class FeverReportConverter {
    public static FeverReportResponseDTO.FeverReportViewDTO toFeverReportViewDTO(FeverReport feverReport,String special) {
        return FeverReportResponseDTO.FeverReportViewDTO.builder()
                .outing(feverReport.getOuting())
                .special(special)
                .etc_symptom(feverReport.getEtc_symptom())
                .symptoms(feverReport.getFeverReportSymptoms().stream().map(FeverReportSymptom::getSymptom).toList())
                .build();
    }

    public static FeverReport toFeverReport(FeverReportRequestDTO.FeverReportCreateDTO feverReportCreateDTO, Child child, String special) {
        return FeverReport.builder()
                .outing(feverReportCreateDTO.getOuting())
                .special(special)
                .child(child)
                .build();
    }
}
