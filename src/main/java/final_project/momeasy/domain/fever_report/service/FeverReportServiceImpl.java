package final_project.momeasy.domain.fever_report.service;

import final_project.momeasy.common.enums.SymptomType;
import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.child.exception.ChildErrorCode;
import final_project.momeasy.domain.child.exception.ChildException;
import final_project.momeasy.domain.child.repository.ChildRepository;
import final_project.momeasy.domain.fever_graph.entity.FeverGraph;
import final_project.momeasy.domain.fever_graph.service.FeverGraphService;
import final_project.momeasy.domain.fever_report.converter.FeverReportConverter;
import final_project.momeasy.domain.fever_report.dto.FeverReportRequestDTO;
import final_project.momeasy.domain.fever_report.dto.FeverReportResponseDTO;
import final_project.momeasy.domain.fever_report.entity.FeverReport;
import final_project.momeasy.domain.fever_report.exception.FeverReportErrorCode;
import final_project.momeasy.domain.fever_report.exception.FeverReportException;
import final_project.momeasy.domain.fever_report.repository.FeverReportRepository;
import final_project.momeasy.domain.fever_report.repository.FeverReportSymptomRepository;
import final_project.momeasy.domain.humidity_graph.entity.HumidityGraph;
import final_project.momeasy.domain.humidity_graph.service.HumidityGraphService;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.symptom.entity.Symptom;
import final_project.momeasy.domain.symptom.repository.SymptomRepository;
import final_project.momeasy.domain.temperature_graph.entity.TemperatureGraph;
import final_project.momeasy.domain.temperature_graph.service.TemperatureGraphService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class FeverReportServiceImpl implements FeverReportService {
    private final FeverReportRepository feverReportRepository;
    private final FeverReportSymptomRepository feverReportSymptomRepository;
    private final ChildRepository childRepository;
    private final SymptomRepository symptomRepository;
    private final FeverGraphService feverGraphService;
    private final HumidityGraphService humidityGraphService;
    private final TemperatureGraphService temperatureGraphService;

    @Override
    public void deleteFeverReport(Parent parent, Long FeverReportId) {
        FeverReport feverReport = feverReportRepository.findById(FeverReportId).orElseThrow(()->new FeverReportException(FeverReportErrorCode.NOT_FOUND));
        Child child = feverReport.getChild();
        if (!childRepository.existsByChildIdAndParentId(child.getId(), parent.getId())) {
            throw new FeverReportException(FeverReportErrorCode.UNAUTHORIZED_ACCESS);
        }
        feverReportRepository.deleteById(FeverReportId);
    }

    // TODO: 특이 사항에 AI 소견 기능 넣기
    @Override
    public FeverReportResponseDTO.FeverReportCreateDTO createFeverReport(Parent parent, FeverReportRequestDTO.FeverReportCreateDTO feverReportRequestDTO, Long ChildId) {
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
        // 체온 그래프, 온도 그래프, 습도 그래프 저장
        List<FeverGraph> feverGraphs = feverGraphService.createFeverRecordGraph(feverReport.getId(), parent, ChildId);
        List<HumidityGraph> humidityGraphs = humidityGraphService.createHumidityRecordGraph(feverReport.getId(), parent, ChildId);
        List<TemperatureGraph> temperatureGraphs = temperatureGraphService.createTemperatureRecordGraph(feverReport.getId(), parent, ChildId);
        return FeverReportConverter.toFeverReportCreateDTO(
                feverReport,feverGraphs,humidityGraphs,temperatureGraphs
        );
    }

    // TODO: 특이 사항에 AI 소견 기능 넣기
    @Override
    public void updateFeverReport(Parent parent, Long FeverReportId, FeverReportRequestDTO.FeverReportUpdateDTO feverReportRequestDTO) {
        FeverReport feverReport = feverReportRepository.findById(FeverReportId).orElseThrow(()->new FeverReportException(FeverReportErrorCode.NOT_FOUND));
        Child child = feverReport.getChild();
        if (!childRepository.existsByChildIdAndParentId(child.getId(), parent.getId())) {
            throw new FeverReportException(FeverReportErrorCode.UNAUTHORIZED_ACCESS);
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
