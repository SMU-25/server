package final_project.momeasy.domain.room_condition.service;

import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.child.exception.ChildErrorCode;
import final_project.momeasy.domain.child.exception.ChildException;
import final_project.momeasy.domain.child.repository.ChildRepository;
import final_project.momeasy.domain.fever_record.exception.FeverRecordErrorCode;
import final_project.momeasy.domain.home_cam.exception.HomecamException;
import final_project.momeasy.domain.home_cam.repository.HomecamRepository;
import final_project.momeasy.domain.room_condition.converter.RoomConditionConverter;
import final_project.momeasy.domain.room_condition.dto.RoomConditionResponseDTO;
import final_project.momeasy.domain.room_condition.entity.RoomCondition;
import final_project.momeasy.domain.room_condition.exception.RoomConditionErrorCode;
import final_project.momeasy.domain.room_condition.exception.RoomConditionException;
import final_project.momeasy.domain.room_condition.repository.RoomConditionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomConditionQueryServiceImpl implements RoomConditionQueryService {
    private final RoomConditionRepository roomConditionRepository;
    private final ChildRepository childRepository;
    private final HomecamRepository homecamRepository;

    @Override
    public RoomConditionResponseDTO.RoomConditionViewDTO getRoomCondition(Long childId) {
        RoomCondition roomCondition =
                roomConditionRepository.findTopByChildIdOrderByIdDesc(childId).orElseThrow(()->new RoomConditionException(RoomConditionErrorCode.NOT_FOUND));
        Child child = childRepository.findById(childId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        if(roomCondition.getChild()!=child){
            throw new RoomConditionException(RoomConditionErrorCode.UNAUTHORIZED_ACCESS);
        }
        return RoomConditionConverter.toRoomConditionViewDTO(roomCondition);
    }

    @Override
    public List<RoomConditionResponseDTO.RoomConditionViewDTO> getRoomConditionPage(Long childId, int page) {
        homecamRepository.findById(childId).orElseThrow(()->new HomecamException(RoomConditionErrorCode.NOT_FOUND));
        Pageable pageable = PageRequest.of(page, 10);
        Slice<RoomCondition> roomConditionList = roomConditionRepository.findAllByChildIdOrderByIdDesc(childId, pageable);
        List<RoomConditionResponseDTO.RoomConditionViewDTO> roomConditionViewDTOList = roomConditionList.stream()
                .map(RoomConditionConverter::toRoomConditionViewDTO).toList();
        return roomConditionViewDTOList;
    }

    @Override
    public List<RoomConditionResponseDTO.RoomConditionViewDTO> getRoomConditionList(Long childId) {
        homecamRepository.findById(childId).orElseThrow(()->new HomecamException(RoomConditionErrorCode.NOT_FOUND));
        List<RoomCondition> roomConditionList = roomConditionRepository.findAllByChildId(childId);
        List<RoomConditionResponseDTO.RoomConditionViewDTO> roomConditionViewDTOList = roomConditionList.stream()
                .map(RoomConditionConverter::toRoomConditionViewDTO).toList();
        return roomConditionViewDTOList;
    }
}
