package final_project.momeasy.domain.fever_report.service;

import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.child.exception.ChildErrorCode;
import final_project.momeasy.domain.child.exception.ChildException;
import final_project.momeasy.domain.child.repository.ChildRepository;
import final_project.momeasy.domain.fever_report.dto.FeverReportRequestDTO;
import final_project.momeasy.domain.fever_report.dto.FeverReportResponseDTO;
import final_project.momeasy.domain.fever_report.entity.FeverReport;
import final_project.momeasy.domain.fever_report.exception.FeverReportErrorCode;
import final_project.momeasy.domain.fever_report.exception.FeverReportException;
import final_project.momeasy.domain.fever_report.repository.FeverReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FeverReportServiceImpl implements FeverReportService {
    private final FeverReportRepository feverReportRepository;
    private final ChildRepository childRepository;

    @Override
    public void deleteFeverReport(Long FeverReportId, Long ChildId) {
        FeverReport feverReport = feverReportRepository.findById(FeverReportId).orElseThrow(()->new FeverReportException(FeverReportErrorCode.NOT_FOUND));
        Child child = childRepository.findById(ChildId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        if(feverReport.getChild()!=child){
            throw new FeverReportException(FeverReportErrorCode.UNAUTHORIZED_ACCESS);
        }
        feverReportRepository.deleteById(FeverReportId);
    }

    @Override
    public FeverReportResponseDTO.FeverReportViewDTO createFeverReport(FeverReportRequestDTO feverReportRequestDTO, Long ChildId) {
        return null;
    }
}
