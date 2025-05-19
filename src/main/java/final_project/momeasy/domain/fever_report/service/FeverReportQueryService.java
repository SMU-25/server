package final_project.momeasy.domain.fever_report.service;

import final_project.momeasy.domain.fever_report.dto.FeverReportRequestDTO;
import final_project.momeasy.domain.fever_report.dto.FeverReportResponseDTO;

import java.util.List;

public interface FeverReportQueryService {
    FeverReportResponseDTO.FeverReportViewDTO getFeverReport(FeverReportRequestDTO feverReportRequestDTO, Long childId);
    List<FeverReportResponseDTO.FeverReportViewDTO> getFeverReports(FeverReportRequestDTO feverReportRequestDTO, Long childId, int page);
}
