package final_project.momeasy.domain.room_condition.service;

import final_project.momeasy.domain.child.exception.ChildErrorCode;
import final_project.momeasy.domain.child.exception.ChildException;
import final_project.momeasy.domain.child.repository.ChildRepository;
import final_project.momeasy.domain.humidity_graph.service.AvgHumidity;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.room_condition.converter.RoomConditionConverter;
import final_project.momeasy.domain.room_condition.dto.RoomConditionResponseDTO;
import final_project.momeasy.domain.room_condition.entity.RoomCondition;
import final_project.momeasy.domain.room_condition.exception.RoomConditionErrorCode;
import final_project.momeasy.domain.room_condition.exception.RoomConditionException;
import final_project.momeasy.domain.room_condition.repository.RoomConditionRepository;
import final_project.momeasy.domain.temperature_graph.service.AvgTemperature;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomConditionQueryServiceImpl implements RoomConditionQueryService {
    private final RoomConditionRepository roomConditionRepository;
    private final ChildRepository childRepository;
    private final AvgHumidity avgHumidity;
    private final AvgTemperature avgTemperature;

    @Override
    public RoomConditionResponseDTO.RoomConditionViewDTO getRoomCondition(Long childId, Parent parent) {
        childRepository.findById(childId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        if (!childRepository.existsByChildIdAndParentId(childId, parent.getId())) {
            throw new RoomConditionException(RoomConditionErrorCode.UNAUTHORIZED_ACCESS);
        }
        RoomCondition roomCondition = roomConditionRepository.findTopByChildIdOrderByCreatedAtDesc(childId).orElseThrow(()->new RoomConditionException(RoomConditionErrorCode.NOT_FOUND));
        return RoomConditionConverter.toRoomConditionViewDTO(roomCondition);
    }

    @Override
    public RoomConditionResponseDTO.RoomConditionListViewDTO getRoomConditionPage(Long childId, Long cursor, Integer size, Parent parent) {
        childRepository.findById(childId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        if (!childRepository.existsByChildIdAndParentId(childId, parent.getId())) {
            throw new RoomConditionException(RoomConditionErrorCode.UNAUTHORIZED_ACCESS);
        }
        Pageable pageable = PageRequest.of(0, size);
        Slice<RoomCondition> roomConditionList = roomConditionRepository.findRoomConditionCursorPagination(childId, cursor, pageable);
        List<RoomCondition> roomConditions = roomConditionList.toList();
        List<RoomConditionResponseDTO.RoomConditionViewDTO> roomConditionViewDTOS = roomConditions.stream().map(RoomConditionConverter::toRoomConditionViewDTO).toList();
        if(roomConditionList.isEmpty()){
            throw new RoomConditionException(RoomConditionErrorCode.NOT_FOUND);
        }

        Long nextCursor = null;
        if(!roomConditions.isEmpty() && roomConditionList.hasNext()) {
            nextCursor = roomConditions.get(roomConditions.size()-1).getId();
        }
        
        return RoomConditionConverter.toRoomConditionListViewDTO(roomConditionViewDTOS,roomConditionList.hasNext(),nextCursor);
    }

    @Override
    public List<RoomConditionResponseDTO.RoomConditionGrpahDTO> getRoomConditionGraphDay1(Long childId, Parent parent) {
        childRepository.findById(childId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        if(!childRepository.existsByChildIdAndParentId(childId, parent.getId())) {
            throw new RoomConditionException(RoomConditionErrorCode.UNAUTHORIZED_ACCESS);
        }
        List<RoomConditionResponseDTO.RoomConditionGrpahDTO> result = new ArrayList<>();
        for(int t = 0 ; t <24 ; t+=3){
            result.add(RoomConditionResponseDTO.RoomConditionGrpahDTO.builder()
                            .avgtemperature(avgHumidity.getHumidityAvgBy3Hour(t,childId))
                            .avghumidity(avgTemperature.getTemperatureAvgBy3Hour(t,childId))
                    .build());
        }
        return result;
    }

    @Override
    public List<RoomConditionResponseDTO.RoomConditionGrpahDTO> getRoomConditionGraphDay3(Long childId, Parent parent) {
        childRepository.findById(childId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        if(!childRepository.existsByChildIdAndParentId(childId, parent.getId())) {
            throw new RoomConditionException(RoomConditionErrorCode.UNAUTHORIZED_ACCESS);
        }
        List<RoomConditionResponseDTO.RoomConditionGrpahDTO> result = new ArrayList<>();
        for(int d=2; d>=0; d--) {
            // 새벽: 00:00 ~ 06:00
            result.add(RoomConditionResponseDTO.RoomConditionGrpahDTO.builder()
                    .avgtemperature(avgHumidity.getHumidityAvgByDayAndTimeRange(d,0,6,childId))
                    .avghumidity(avgTemperature.getTemperatureAvgByDayAndTimeRange(d,0,6,childId))
                    .build());

            // 오전: 06:00 ~ 12:00
            result.add(RoomConditionResponseDTO.RoomConditionGrpahDTO.builder()
                    .avgtemperature(avgHumidity.getHumidityAvgByDayAndTimeRange(d,6,12,childId))
                    .avghumidity(avgTemperature.getTemperatureAvgByDayAndTimeRange(d,6,12,childId))
                    .build());

            // 오후: 12:00 ~ 24:00
            result.add(RoomConditionResponseDTO.RoomConditionGrpahDTO.builder()
                    .avghumidity(avgHumidity.getHumidityAvgByDayAndTimeRange(d,12,24,childId))
                    .avgtemperature(avgTemperature.getTemperatureAvgByDayAndTimeRange(d,12,24,childId))
                    .build());
        }
        return result;
    }

    @Override
    public List<RoomConditionResponseDTO.RoomConditionGrpahDTO> getRoomConditionGraphDay7(Long childId, Parent parent) {
        childRepository.findById(childId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        if(!childRepository.existsByChildIdAndParentId(childId, parent.getId())) {
            throw new RoomConditionException(RoomConditionErrorCode.UNAUTHORIZED_ACCESS);
        }
        List<RoomConditionResponseDTO.RoomConditionGrpahDTO> result = new ArrayList<>();
        for(int d = 6; d>=0; d--){
            result.add(RoomConditionResponseDTO.RoomConditionGrpahDTO.builder()
                    .avghumidity(avgHumidity.getHumidityAvgByDayAndTimeRange(d,0,24,childId))
                    .avgtemperature(avgTemperature.getTemperatureAvgByDayAndTimeRange(d,0,24,childId))
                    .build());
        }
        return result;
    }
}
