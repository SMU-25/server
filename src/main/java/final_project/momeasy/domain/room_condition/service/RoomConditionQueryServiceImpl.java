package final_project.momeasy.domain.room_condition.service;

import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.child.exception.ChildErrorCode;
import final_project.momeasy.domain.child.exception.ChildException;
import final_project.momeasy.domain.child.repository.ChildRepository;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.parent.entity.ParentChild;
import final_project.momeasy.domain.room_condition.converter.RoomConditionConverter;
import final_project.momeasy.domain.room_condition.dto.RoomConditionResponseDTO;
import final_project.momeasy.domain.room_condition.entity.RoomCondition;
import final_project.momeasy.domain.room_condition.exception.RoomConditionErrorCode;
import final_project.momeasy.domain.room_condition.exception.RoomConditionException;
import final_project.momeasy.domain.room_condition.repository.RoomConditionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomConditionQueryServiceImpl implements RoomConditionQueryService {
    private final RoomConditionRepository roomConditionRepository;
    private final ChildRepository childRepository;

    @Override
    public RoomConditionResponseDTO.RoomConditionViewDTO getRoomCondition(Long childId, Parent parent) {
        Child child = childRepository.findById(childId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        List<ParentChild> parentChildren = child.getParentChildren();
        RoomCondition roomCondition = null;
        for(ParentChild parentChild : parentChildren) {
            if(parentChild.getParent().equals(parent)) {
                roomCondition = roomConditionRepository.findTopByChildIdOrderByCreatedAtDesc(childId).orElseThrow(()->new RoomConditionException(RoomConditionErrorCode.NOT_FOUND));
            }
        }
        if(roomCondition == null) {
            throw new RoomConditionException(RoomConditionErrorCode.UNAUTHORIZED_ACCESS);
        }
        return RoomConditionConverter.toRoomConditionViewDTO(roomCondition);
    }

    @Override
    public List<RoomConditionResponseDTO.RoomConditionViewDTO> getRoomConditionPage(Long childId, int page, Parent parent) {
        Child child = childRepository.findById(childId).orElseThrow(()->new ChildException(ChildErrorCode.NOT_FOUND));
        List<ParentChild> parentChildren = child.getParentChildren();
        Slice<RoomCondition> roomConditionList = null;
        for(ParentChild parentChild : parentChildren) {
            if(parentChild.getParent().equals(parent)) {
                Pageable pageable = PageRequest.of(page, 10);
                roomConditionList = roomConditionRepository.findAllByChildIdOrderByCreatedAtDesc(childId, pageable);
            }
        }
        if(roomConditionList == null) {
            throw new RoomConditionException(RoomConditionErrorCode.UNAUTHORIZED_ACCESS);
        }
        if(roomConditionList.isEmpty()){
            throw new RoomConditionException(RoomConditionErrorCode.NOT_FOUND);
        }
        List<RoomConditionResponseDTO.RoomConditionViewDTO> roomConditionViewDTOList = roomConditionList.stream()
                .map(RoomConditionConverter::toRoomConditionViewDTO).toList();
        return roomConditionViewDTOList;
    }
}
