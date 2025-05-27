package final_project.momeasy.domain.fever_report.service;

import final_project.momeasy.domain.fever_report.dto.FeverReportResponseDTO;
import final_project.momeasy.domain.parent.entity.Parent;

import java.util.List;

public interface FeverReportQueryService {
    FeverReportResponseDTO.FeverReportViewDTO getFeverReport(Parent parent, Long childId, Long reportId);
    List<FeverReportResponseDTO.FeverReportViewDTO> getFeverReports(Parent parent, Long childId, int page);
}
