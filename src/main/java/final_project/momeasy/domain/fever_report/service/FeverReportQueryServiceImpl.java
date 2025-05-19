package final_project.momeasy.domain.fever_report.service;

import final_project.momeasy.domain.fever_report.converter.FeverReportConverter;
import final_project.momeasy.domain.fever_report.dto.FeverReportRequestDTO;
import final_project.momeasy.domain.fever_report.dto.FeverReportResponseDTO;
import final_project.momeasy.domain.fever_report.entity.FeverReport;
import final_project.momeasy.domain.fever_report.exception.FeverReportErrorCode;
import final_project.momeasy.domain.fever_report.exception.FeverReportException;
import final_project.momeasy.domain.fever_report.repository.FeverReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeverReportQueryServiceImpl implements FeverReportQueryService {
    private final FeverReportRepository feverReportRepository;

    @Override
    public FeverReportResponseDTO.FeverReportViewDTO getFeverReport(FeverReportRequestDTO feverReportRequestDTO, Long childId) {
        FeverReport feverReport = feverReportRepository.findByChildId(childId).orElseThrow(()->new FeverReportException(FeverReportErrorCode.NOT_FOUND));
        String special = "특이 사항";
        return FeverReportConverter.toFeverReportViewDTO(feverReport,special);
    }

    @Override
    public List<FeverReportResponseDTO.FeverReportViewDTO> getFeverReports(FeverReportRequestDTO feverReportRequestDTO, Long childId, int page) {
        Pageable pageable = PageRequest.of(page,10);
        String special = "특이 사항";
        Slice<FeverReport> feverReportSlice = feverReportRepository.findAllByChildIdOrderByIdDesc(childId,pageable);
        List<FeverReportResponseDTO.FeverReportViewDTO> feverReportList = feverReportSlice.stream().map(feverReport -> FeverReportConverter.toFeverReportViewDTO(feverReport,special)).toList();
        return feverReportList;
    }
}
