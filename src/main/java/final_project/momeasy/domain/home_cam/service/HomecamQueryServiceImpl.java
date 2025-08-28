package final_project.momeasy.domain.home_cam.service;

import final_project.momeasy.domain.fever_graph.dto.FeverGraphResponseDTO;
import final_project.momeasy.domain.fever_graph.service.FeverGraphService;
import final_project.momeasy.domain.home_cam.converter.HomecamConverter;
import final_project.momeasy.domain.home_cam.dto.HomecamResponseDTO;
import final_project.momeasy.domain.home_cam.entity.Homecam;
import final_project.momeasy.domain.home_cam.exception.HomecamErrorCode;
import final_project.momeasy.domain.home_cam.exception.HomecamException;
import final_project.momeasy.domain.home_cam.repository.HomecamRepository;
import final_project.momeasy.domain.humidity_graph.dto.HumidityGraphResponseDTO;
import final_project.momeasy.domain.humidity_graph.service.HumidityGraphService;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.temperature_graph.dto.TemperatureGraphResponseDTO;
import final_project.momeasy.domain.temperature_graph.service.TemperatureGraphService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomecamQueryServiceImpl implements HomecamQueryService {
    private final HomecamRepository homecamRepository;
    private final FeverGraphService feverGraphService;
    private final HumidityGraphService humidityGraphService;
    private final TemperatureGraphService temperatureGraphService;

    @Override
    public HomecamResponseDTO.HomecamDetailDTO getHomecamById(Long homecamId, Parent parent) {
        Homecam homecam = homecamRepository.findById(homecamId).orElseThrow(()->new HomecamException(HomecamErrorCode.NOT_FOUND));
        if(!homecam.getParent().equals(parent)) {
            throw new HomecamException(HomecamErrorCode.UNAUTHORIZED_ACCESS);
        }
        return HomecamConverter.toHomecamDetailDTO(homecam);
    }

    @Override
    public List<HomecamResponseDTO.HomecamDTO> getHomecamListByParent(Parent parent) {
        List<Homecam> homecamList = homecamRepository.findAllByParentIdOrderByIdDesc(parent.getId());
        if(homecamList.isEmpty()){
            throw new HomecamException(HomecamErrorCode.NOT_FOUND);
        }
        List<HomecamResponseDTO.HomecamDTO> homecamListDto
        = homecamList.stream().map(HomecamConverter::toHomecamDTO).toList();
        return homecamListDto;
    }

    @Override
    public HomecamResponseDTO.HomecamGraphDTO getHomecamGraphById(Long homecamId, Parent parent) {
        Homecam homecam = homecamRepository.findById(homecamId).orElseThrow(()->new HomecamException(HomecamErrorCode.NOT_FOUND));
        if(!homecam.getParent().equals(parent)) {
            throw new HomecamException(HomecamErrorCode.UNAUTHORIZED_ACCESS);
        }
        Long childId = homecam.getChild().getId();
        List<FeverGraphResponseDTO.FeverGraphHomecamViewDTO> feverGraphs = feverGraphService.getHomeCamFeverRecordGraph(parent, childId);
        List<HumidityGraphResponseDTO.HumidityGraphHomecamViewDTO> humidityGraphs = humidityGraphService.getHumidityRecordHomecamGraph(parent, childId);
        List<TemperatureGraphResponseDTO.TemperatureGraphHomecamViewDTO> temperatureGraphs = temperatureGraphService.getTemperatureRecordHomecamGraph(parent, childId);
        return HomecamConverter.toHomecamGraphViewDTO(feverGraphs,humidityGraphs,temperatureGraphs);
    }
}
