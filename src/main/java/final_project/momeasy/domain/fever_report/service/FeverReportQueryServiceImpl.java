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
        Child child = childRepository.findById(childId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        List<ParentChild> parentChildren = child.getParentChildren();
        FeverReport feverReport = null;
        for (ParentChild parentChild : parentChildren) {
            if(parentChild.getParent().getId().equals(parent.getId())) {
                feverReport = feverReportRepository.findById(reportId).orElseThrow(()->new FeverReportException(FeverReportErrorCode.NOT_FOUND));
                break;
            }
        }
        if(feverReport == null) {
            throw new FeverReportException(FeverReportErrorCode.UNAUTHORIZED_ACCESS);
        }
        return FeverReportConverter.toFeverReportViewDTO(feverReport);
    }

    @Override
    public List<FeverReportResponseDTO.FeverReportViewDTO> getFeverReports(Parent parent, Long childId, int page) {
        Pageable pageable = PageRequest.of(page,10);
        Child child = childRepository.findById(childId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        List<ParentChild> parentChildren = child.getParentChildren();
        Slice<FeverReport> feverReportSlice = null;
        for(ParentChild parentChild : parentChildren) {
            if(parentChild.getParent().getId().equals(parent.getId())) {
                feverReportSlice = feverReportRepository.findAllByChildIdOrderByIdDesc(childId,pageable);
                break;
            }
        }
        if(feverReportSlice == null) {
            throw new FeverReportException(FeverReportErrorCode.UNAUTHORIZED_ACCESS);
        }
        List<FeverReportResponseDTO.FeverReportViewDTO> feverReportList = feverReportSlice.stream().map(feverReport -> FeverReportConverter.toFeverReportViewDTO(feverReport)).toList();
        return feverReportList;
    }
}
