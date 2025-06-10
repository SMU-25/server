package final_project.momeasy.domain.fever_report.service;

import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.child.exception.ChildErrorCode;
import final_project.momeasy.domain.child.exception.ChildException;
import final_project.momeasy.domain.child.repository.ChildRepository;
import final_project.momeasy.domain.fever_report.converter.FeverReportConverter;
import final_project.momeasy.domain.fever_report.dto.FeverReportResponseDTO;
import final_project.momeasy.domain.fever_report.entity.FeverReport;
import final_project.momeasy.domain.fever_report.exception.FeverReportErrorCode;
import final_project.momeasy.domain.fever_report.exception.FeverReportException;
import final_project.momeasy.domain.fever_report.repository.FeverReportRepository;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.parent.entity.ParentChild;
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
    private final ChildRepository childRepository;

    @Override
    public FeverReportResponseDTO.FeverReportViewDTO getFeverReport(Parent parent, Long childId, Long reportId) {
        childRepository.findById(childId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        if (!childRepository.existsByChildIdAndParentId(childId, parent.getId())) {
            throw new ChildException(ChildErrorCode.UNAUTHORIZED_ACCESS);
        }
        FeverReport feverReport = feverReportRepository.findById(reportId).orElseThrow(()->new FeverReportException(FeverReportErrorCode.NOT_FOUND));
        return FeverReportConverter.toFeverReportViewDTO(feverReport);
    }

    @Override
    public List<FeverReportResponseDTO.FeverReportViewDTO> getFeverReports(Parent parent, Long childId, int page) {
        childRepository.findById(childId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        if (!childRepository.existsByChildIdAndParentId(childId, parent.getId())) {
            throw new ChildException(ChildErrorCode.UNAUTHORIZED_ACCESS);
        }
        Pageable pageable = PageRequest.of(page,10);
        Slice<FeverReport> feverReportSlice = feverReportRepository.findAllByChildIdOrderByIdDesc(childId,pageable);
        if(feverReportSlice.isEmpty()){
            throw new FeverReportException(FeverReportErrorCode.NOT_FOUND);
        }
        List<FeverReportResponseDTO.FeverReportViewDTO> feverReportList = feverReportSlice.stream().map(feverReport -> FeverReportConverter.toFeverReportViewDTO(feverReport)).toList();
        return feverReportList;
    }

    @Override
    public List<FeverReportResponseDTO.FeverReportViewDTO> getFeverReportList(Parent parent, Long childId) {
        childRepository.findById(childId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        if (!childRepository.existsByChildIdAndParentId(childId, parent.getId())) {
            throw new ChildException(ChildErrorCode.UNAUTHORIZED_ACCESS);
        }
        List<FeverReport> feverReportSlice = feverReportRepository.findAllByChildIdOrderByIdDesc(childId);
        if(feverReportSlice.isEmpty()){
            throw new FeverReportException(FeverReportErrorCode.NOT_FOUND);
        }
        List<FeverReportResponseDTO.FeverReportViewDTO> feverReportList = feverReportSlice.stream().map(feverReport -> FeverReportConverter.toFeverReportViewDTO(feverReport)).toList();
        return feverReportList;
    }
}
