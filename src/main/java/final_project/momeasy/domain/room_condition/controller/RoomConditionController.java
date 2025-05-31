package final_project.momeasy.domain.room_condition.controller;

import final_project.momeasy.domain.room_condition.dto.RoomConditionResponseDTO;
import final_project.momeasy.domain.room_condition.service.RoomConditionQueryService;
import final_project.momeasy.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/rooms")
@Tag(name = "RoomCondition", description = "RoomCondition Controller")
public class RoomConditionController {
    private final RoomConditionQueryService roomConditionQueryService;

    @GetMapping("/{childId}")
    @Operation(summary = "최근 방 온습도 기록 조회 API", description = "최근 방 온습도 기록을 조회합니다.")
    public CustomResponse<RoomConditionResponseDTO.RoomConditionViewDTO> getRoomCondition(@PathVariable("childId") Long childId) {
        RoomConditionResponseDTO.RoomConditionViewDTO roomConditionViewDTO = roomConditionQueryService.getRoomCondition(childId);
        return CustomResponse.onSuccess(roomConditionViewDTO);
    }

    @GetMapping("list/{childId}")
    @Operation(summary = "방 온습도 기록 조회 API", description = "최근 방 온습도 기록 10개를 조회합니다.")
    public CustomResponse<List<RoomConditionResponseDTO.RoomConditionViewDTO>> getRoomConditionPage(@PathVariable("childId") Long childId
    ,@RequestParam(value="page", defaultValue="0") int page) {
        List<RoomConditionResponseDTO.RoomConditionViewDTO> roomConditionViewList = roomConditionQueryService.getRoomConditionPage(childId, page);
        return CustomResponse.onSuccess(roomConditionViewList);
    }

    @GetMapping("all/{childId}")
    @Operation(summary = "전체 방 온습도 기록 조회 API", description = "전체 방 온습도 기록을 조회합니다.")
    public CustomResponse<List<RoomConditionResponseDTO.RoomConditionViewDTO>> getRoomConditionList(@PathVariable("childId") Long childId) {
        List<RoomConditionResponseDTO.RoomConditionViewDTO> roomConditionViewList= roomConditionQueryService.getRoomConditionList(childId);
        return CustomResponse.onSuccess(roomConditionViewList);
    }
}
