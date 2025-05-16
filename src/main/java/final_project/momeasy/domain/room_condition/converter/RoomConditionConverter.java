package final_project.momeasy.domain.room_condition.converter;

import final_project.momeasy.domain.child.entity.Child;
import final_project.momeasy.domain.room_condition.dto.RoomConditionRequestDTO;
import final_project.momeasy.domain.room_condition.dto.RoomConditionResponseDTO;
import final_project.momeasy.domain.room_condition.entity.RoomCondition;

public class RoomConditionConverter {
    public static RoomConditionResponseDTO.RoomConditionViewDTO toRoomConditionViewDTO(RoomCondition roomCondition) {
        return RoomConditionResponseDTO.RoomConditionViewDTO.builder()
                .temperature(roomCondition.getTemperature())
                .humidity(roomCondition.getHumidity())
                .build();
    }

    public static RoomCondition toRoomCondition(RoomConditionRequestDTO.RoomConditionCreateDTO roomConditionCreateDTO, Child child) {
        return RoomCondition.builder()
                .temperature(roomConditionCreateDTO.getTemperature())
                .humidity(roomConditionCreateDTO.getHumidity())
                .child(child)
                .build();
    }
}
