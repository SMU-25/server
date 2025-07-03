package final_project.momeasy.domain.fever_report.service;

import final_project.momeasy.domain.fever_report.dto.FeverReportResponseDTO;
import final_project.momeasy.domain.parent.entity.Parent;

import java.util.List;

public interface FeverReportQueryService {
    FeverReportResponseDTO.FeverReportDetailViewDTO getFeverReport(Parent parent, Long reportId);
    FeverReportResponseDTO.FeverReportListViewDTO getFeverReports(Parent parent,Long cursor, Integer size,Long childId);
    List<FeverReportResponseDTO.FeverReportViewDTO> getFeverReportList(Parent parent, Long childId);

}
