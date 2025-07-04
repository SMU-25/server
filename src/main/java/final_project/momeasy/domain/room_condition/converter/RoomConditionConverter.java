package final_project.momeasy.domain.room_condition.converter;

import final_project.momeasy.domain.room_condition.dto.RoomConditionResponseDTO;
import final_project.momeasy.domain.room_condition.entity.RoomCondition;

import java.util.List;

public class RoomConditionConverter {
    public static RoomConditionResponseDTO.RoomConditionViewDTO toRoomConditionViewDTO(RoomCondition roomCondition) {
        return RoomConditionResponseDTO.RoomConditionViewDTO.builder()
                .temperature(roomCondition.getTemperature())
                .humidity(roomCondition.getHumidity())
                .createdAt(roomCondition.getCreatedAt())
                .build();
    }

    public static RoomConditionResponseDTO.RoomConditionListViewDTO toRoomConditionListViewDTO(List<RoomConditionResponseDTO.RoomConditionViewDTO> roomConditions,
        Boolean hasNext, Long cursor) {
        return RoomConditionResponseDTO.RoomConditionListViewDTO.builder()
                .roomConditions(roomConditions)
                .cursor(cursor)
                .hasNext(hasNext)
                .build();
    }
}
