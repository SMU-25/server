package final_project.momeasy.domain.fever_report.service;

import final_project.momeasy.domain.fever_report.dto.FeverReportRequestDTO;
import final_project.momeasy.domain.fever_report.dto.FeverReportResponseDTO;

public interface FeverReportService {
    void deleteFeverReport(Long FeverReportId, Long ChildId);
    FeverReportResponseDTO.FeverReportViewDTO createFeverReport(FeverReportRequestDTO feverReportRequestDTO, Long ChildId);
}
