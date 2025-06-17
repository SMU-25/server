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
import final_project.momeasy.domain.fever_report.repository.FeverReportSymptomRepository;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.symptom.entity.Symptom;
import final_project.momeasy.domain.symptom.repository.SymptomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
@Transactional
public class FeverReportServiceImpl implements FeverReportService {
    private final FeverReportRepository feverReportRepository;
    private final FeverReportSymptomRepository feverReportSymptomRepository;
    private final ChildRepository childRepository;
    private final SymptomRepository symptomRepository;

    @Override
    public void deleteFeverReport(Parent parent, Long FeverReportId, Long ChildId) {
        childRepository.findById(ChildId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        if (!childRepository.existsByChildIdAndParentId(ChildId, parent.getId())) {
            throw new ChildException(ChildErrorCode.UNAUTHORIZED_ACCESS);
        }
        feverReportRepository.findById(FeverReportId).orElseThrow(()->new FeverReportException(FeverReportErrorCode.NOT_FOUND));
        feverReportRepository.deleteById(FeverReportId);
    }

    // TODO: 특이 사항에 AI 소견 기능 넣기
    @Override
    public FeverReportResponseDTO.FeverReportViewDTO createFeverReport(Parent parent, FeverReportRequestDTO.FeverReportCreateDTO feverReportRequestDTO, Long ChildId) {
        Child child = childRepository.findById(ChildId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        if (!childRepository.existsByChildIdAndParentId(ChildId, parent.getId())) {
            throw new ChildException(ChildErrorCode.UNAUTHORIZED_ACCESS);
        }
        String special = "특이 사항";
        FeverReport feverReport = FeverReportConverter.toFeverReport(feverReportRequestDTO,special);
        feverReport.setChild(child);
        feverReportRepository.save(feverReport);
        for(SymptomType symptomtype: feverReportRequestDTO.getSymptoms()) {
            Symptom symptom = symptomRepository.findBySymptom(symptomtype).orElseGet(()-> symptomRepository.save(
                    Symptom.builder()
                    .symptom(symptomtype)
                    .build()));
            symptom.addFeverReport(feverReport);
        }
        return FeverReportConverter.toFeverReportViewDTO(feverReport);
    }

    // TODO: 특이 사항에 AI 소견 기능 넣기
    @Override
    public void updateFeverReport(Parent parent, Long FeverReportId, Long ChildId, FeverReportRequestDTO.FeverReportUpdateDTO feverReportRequestDTO) {
        FeverReport feverReport = feverReportRepository.findById(FeverReportId).orElseThrow(()->new FeverReportException(FeverReportErrorCode.NOT_FOUND));
        Child child = childRepository.findById(ChildId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        if (!childRepository.existsByChildIdAndParentId(ChildId, parent.getId())) {
            throw new ChildException(ChildErrorCode.UNAUTHORIZED_ACCESS);
        }
        String special = "특이 사항";
        feverReport.updateFeverReport(feverReportRequestDTO,special);
        feverReport.getFeverReportSymptoms().clear();
        feverReportSymptomRepository.deleteByFeverreportId(FeverReportId);
        for(SymptomType symptomtype: feverReportRequestDTO.getSymptoms()) {
            Symptom symptom = symptomRepository.findBySymptom(symptomtype).orElseGet(()->
            symptomRepository.save(Symptom.builder()
                            .symptom(symptomtype)
                            .build()));
            symptom.addFeverReport(feverReport);
        }
    }
}
