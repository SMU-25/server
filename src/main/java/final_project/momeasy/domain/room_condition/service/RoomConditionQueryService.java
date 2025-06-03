package final_project.momeasy.domain.room_condition.service;

import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.room_condition.dto.RoomConditionResponseDTO;

import java.util.List;

public interface RoomConditionQueryService {
    RoomConditionResponseDTO.RoomConditionViewDTO getRoomCondition(Long childId, Parent parent);

    List<RoomConditionResponseDTO.RoomConditionViewDTO> getRoomConditionPage(Long childId, int page, Parent parent);
}
