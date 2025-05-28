package final_project.momeasy.domain.room_condition.controller;

import final_project.momeasy.domain.room_condition.dto.RoomConditionResponseDTO;
import final_project.momeasy.domain.room_condition.service.RoomConditionQueryService;
import final_project.momeasy.global.apiPayload.CustomResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/rooms")
public class RoomConditionController {
    private final RoomConditionQueryService roomConditionQueryService;

    @GetMapping("/{childId}")
    public CustomResponse<RoomConditionResponseDTO.RoomConditionViewDTO> getRoomCondition(@PathVariable("childId") Long childId) {
        RoomConditionResponseDTO.RoomConditionViewDTO roomConditionViewDTO = roomConditionQueryService.getRoomCondition(childId);
        return CustomResponse.onSuccess(roomConditionViewDTO);
    }

    @GetMapping("list/{childId}")
    public CustomResponse<List<RoomConditionResponseDTO.RoomConditionViewDTO>> getRoomConditionPage(@PathVariable("childId") Long childId
    ,@RequestParam(value="page", defaultValue="0") int page) {
        List<RoomConditionResponseDTO.RoomConditionViewDTO> roomConditionViewList = roomConditionQueryService.getRoomConditionPage(childId, page);
        return CustomResponse.onSuccess(roomConditionViewList);
    }

    @GetMapping("all/{childId}")
    public CustomResponse<List<RoomConditionResponseDTO.RoomConditionViewDTO>> getRoomConditionList(@PathVariable("childId") Long childId) {
        List<RoomConditionResponseDTO.RoomConditionViewDTO> roomConditionViewList= roomConditionQueryService.getRoomConditionList(childId);
        return CustomResponse.onSuccess(roomConditionViewList);
    }
}
