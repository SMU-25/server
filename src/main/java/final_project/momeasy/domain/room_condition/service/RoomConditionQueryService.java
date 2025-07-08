package final_project.momeasy.domain.room_condition.service;

import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.room_condition.dto.RoomConditionResponseDTO;

import java.util.List;

public interface RoomConditionQueryService {
    RoomConditionResponseDTO.RoomConditionViewDTO getRoomCondition(Long childId, Parent parent);

    RoomConditionResponseDTO.RoomConditionListViewDTO getRoomConditionPage(Long childId, Long cursor, Integer size, Parent parent);

    List<RoomConditionResponseDTO.RoomConditionGrpahDTO> getRoomConditionGraphDay1(Long childId, Parent parent);

    List<RoomConditionResponseDTO.RoomConditionGrpahDTO> getRoomConditionGraphDay3(Long childId, Parent parent);

    List<RoomConditionResponseDTO.RoomConditionGrpahDTO> getRoomConditionGraphDay7(Long childId, Parent parent);

}
