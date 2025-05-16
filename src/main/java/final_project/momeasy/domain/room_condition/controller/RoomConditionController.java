package final_project.momeasy.domain.room_condition.controller;

import final_project.momeasy.domain.room_condition.dto.RoomConditionResponseDTO;
import final_project.momeasy.domain.room_condition.service.RoomConditionQueryService;
import final_project.momeasy.global.apiPayload.CustomResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/room")
public class RoomConditionController {
    private final RoomConditionQueryService roomConditionQueryService;

    @GetMapping("/{child_id}")
    public CustomResponse<RoomConditionResponseDTO.RoomConditionViewDTO> getRoomCondition(@PathVariable("child_id") Long childId) {
        RoomConditionResponseDTO.RoomConditionViewDTO roomConditionViewDTO = roomConditionQueryService.getRoomCondition(childId);
        return CustomResponse.onSuccess(roomConditionViewDTO);
    }

    @GetMapping("list/{child_id}")
    public CustomResponse<List<RoomConditionResponseDTO.RoomConditionViewDTO>> getRoomConditionPage(@PathVariable("child_id") Long childId
    ,@RequestParam(value="page", defaultValue="0") int page) {
        List<RoomConditionResponseDTO.RoomConditionViewDTO> roomConditionViewList = roomConditionQueryService.getRoomConditionPage(childId, page);
        return CustomResponse.onSuccess(roomConditionViewList);
    }

    @GetMapping("all/{child_id}")
    public CustomResponse<List<RoomConditionResponseDTO.RoomConditionViewDTO>> getRoomConditionList(@PathVariable("child_id") Long childId) {
        List<RoomConditionResponseDTO.RoomConditionViewDTO> roomConditionViewList= roomConditionQueryService.getRoomConditionList(childId);
        return CustomResponse.onSuccess(roomConditionViewList);
    }
}
