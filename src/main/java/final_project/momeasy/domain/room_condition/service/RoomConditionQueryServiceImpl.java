package final_project.momeasy.domain.room_condition.service;

import final_project.momeasy.domain.child.repository.ChildRepository;
import final_project.momeasy.domain.parent.entity.Parent;
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
    private final RoomConditionGraphGenerator roomConditionGraphGenerator;

    @Override
    public RoomConditionResponseDTO.RoomConditionViewDTO getRoomCondition(Long childId, Parent parent) {
        validateChildAccess(childId, parent);
        RoomCondition roomCondition = roomConditionRepository.findTopByChildIdOrderByCreatedAtDesc(childId).orElseThrow(() -> new RoomConditionException(RoomConditionErrorCode.NOT_FOUND));
        return RoomConditionConverter.toRoomConditionViewDTO(roomCondition);
    }

    @Override
    public RoomConditionResponseDTO.RoomConditionListViewDTO getRoomConditionPage(Long childId, Long cursor, Integer size, Parent parent) {
        validateChildAccess(childId, parent);

        if (cursor == 0) {
            cursor = Long.MAX_VALUE;
        }
        Pageable pageable = PageRequest.of(0, size);
        Slice<RoomCondition> roomConditionList = roomConditionRepository.findRoomConditionCursorPagination(childId, cursor, pageable);
        List<RoomCondition> roomConditions = roomConditionList.toList();
        List<RoomConditionResponseDTO.RoomConditionViewDTO> roomConditionViewDTOS = roomConditions.stream().map(RoomConditionConverter::toRoomConditionViewDTO).toList();
        if (roomConditionList.isEmpty()) {
            throw new RoomConditionException(RoomConditionErrorCode.NOT_FOUND);
        }

        Long nextCursor = null;
        if (!roomConditions.isEmpty() && roomConditionList.hasNext()) {
            nextCursor = roomConditions.get(roomConditions.size() - 1).getId();
        }

        return RoomConditionConverter.toRoomConditionListViewDTO(roomConditionViewDTOS, roomConditionList.hasNext(), nextCursor);
    }

    @Override
    public List<RoomConditionResponseDTO.RoomConditionGrpahDTO> getRoomConditionGraphDay1(Long childId, Parent parent) {
        validateChildAccess(childId, parent);
        return roomConditionGraphGenerator.createDay1(childId);
    }

    @Override
    public List<RoomConditionResponseDTO.RoomConditionGrpahDTO> getRoomConditionGraphDay3(Long childId, Parent parent) {
        validateChildAccess(childId, parent);
        return roomConditionGraphGenerator.createDay3(childId);
    }

    @Override
    public List<RoomConditionResponseDTO.RoomConditionGrpahDTO> getRoomConditionGraphDay7(Long childId, Parent parent) {
        validateChildAccess(childId, parent);
        return roomConditionGraphGenerator.createDay7(childId);
    }

    private void validateChildAccess(Long childId, Parent parent) {
        childRepository.findByIdAndParentId(childId, parent.getId())
                .orElseThrow(() ->
                        new RoomConditionException(RoomConditionErrorCode.UNAUTHORIZED_ACCESS)
                );
    }
}
