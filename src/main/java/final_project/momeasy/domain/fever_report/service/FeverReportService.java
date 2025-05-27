package final_project.momeasy.domain.fever_report.service;

import final_project.momeasy.domain.fever_report.dto.FeverReportRequestDTO;
import final_project.momeasy.domain.fever_report.dto.FeverReportResponseDTO;
import final_project.momeasy.domain.parent.entity.Parent;

public interface FeverReportService {
    void deleteFeverReport(Parent parent, Long FeverReportId, Long ChildId);
    FeverReportResponseDTO.FeverReportViewDTO createFeverReport(Parent parent, FeverReportRequestDTO.FeverReportCreateDTO feverReportRequestDTO, Long ChildId);
}
