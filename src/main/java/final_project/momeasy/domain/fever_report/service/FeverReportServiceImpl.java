package final_project.momeasy.domain.fever_report.service;

import final_project.momeasy.common.enums.SymptomType;
import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.child.exception.ChildErrorCode;
import final_project.momeasy.domain.child.exception.ChildException;
import final_project.momeasy.domain.child.repository.ChildRepository;
import final_project.momeasy.domain.fever_report.converter.FeverReportConverter;
import final_project.momeasy.domain.fever_report.dto.FeverReportRequestDTO;
import final_project.momeasy.domain.fever_report.dto.FeverReportResponseDTO;
import final_project.momeasy.domain.fever_report.entity.FeverReport;
import final_project.momeasy.domain.fever_report.exception.FeverReportErrorCode;
import final_project.momeasy.domain.fever_report.exception.FeverReportException;
import final_project.momeasy.domain.fever_report.repository.FeverReportRepository;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.parent.entity.ParentChild;
import final_project.momeasy.domain.symptom.entity.Symptom;
import final_project.momeasy.domain.symptom.repository.SymptomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class FeverReportServiceImpl implements FeverReportService {
    private final FeverReportRepository feverReportRepository;
    private final ChildRepository childRepository;
    private final SymptomRepository symptomRepository;

    @Override
    public void deleteFeverReport(Parent parent, Long FeverReportId, Long ChildId) {
        Child child = childRepository.findById(ChildId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        List<ParentChild> parentChildren = child.getParentChildren();
        FeverReport feverReport = null;
        for(ParentChild parentChild : parentChildren) {
            if(parentChild.getParent().equals(parent)) {
                feverReport = feverReportRepository.findById(FeverReportId).orElseThrow(()->new FeverReportException(FeverReportErrorCode.NOT_FOUND));
            }
        }
        if(feverReport == null) {
            throw new FeverReportException(FeverReportErrorCode.UNAUTHORIZED_ACCESS);
        }
        feverReportRepository.deleteById(FeverReportId);
    }

    @Override
    public FeverReportResponseDTO.FeverReportViewDTO createFeverReport(Parent parent, FeverReportRequestDTO.FeverReportCreateDTO feverReportRequestDTO, Long ChildId) {
        Child child = childRepository.findById(ChildId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        List<ParentChild> parentChildren = child.getParentChildren();
        FeverReport feverReport = null;
        for(ParentChild parentChild : parentChildren) {
            if(parentChild.getParent().equals(parent)) {
                String special = "특이 사항";
                feverReport = FeverReportConverter.toFeverReport(feverReportRequestDTO,special);
                feverReport.setChild(child);
                feverReportRepository.save(feverReport);
                for(SymptomType symptomtype: feverReportRequestDTO.getSymptoms()) {
                    Symptom symptom = Symptom.builder()
                            .symptom(symptomtype)
                            .build();
                    symptom.addFeverReport(feverReport);
                    symptomRepository.save(symptom);
                }
            }
        }
        if(feverReport == null) {
            throw new FeverReportException(FeverReportErrorCode.UNAUTHORIZED_ACCESS);
        }
        return FeverReportConverter.toFeverReportViewDTO(feverReport);
    }
}
