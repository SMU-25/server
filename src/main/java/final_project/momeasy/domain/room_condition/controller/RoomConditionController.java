package final_project.momeasy.domain.room_condition.controller;

import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.room_condition.dto.RoomConditionResponseDTO;
import final_project.momeasy.domain.room_condition.service.RoomConditionQueryService;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.security.annotation.AuthParent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/rooms")
@Tag(name = "RoomCondition", description = "온습도 기록 API")
public class RoomConditionController {
    private final RoomConditionQueryService roomConditionQueryService;

    @GetMapping("/{childId}")
    @Operation(summary = "최근 온습도 기록 조회", description = "최근 방 온습도 기록을 조회합니다.")
    public CustomResponse<RoomConditionResponseDTO.RoomConditionViewDTO> getRoomCondition(@PathVariable("childId") Long childId, @AuthParent Parent parent) {
        RoomConditionResponseDTO.RoomConditionViewDTO roomConditionViewDTO = roomConditionQueryService.getRoomCondition(childId, parent);
        return CustomResponse.onSuccess(roomConditionViewDTO);
    }

    @GetMapping("list/{childId}")
    @Operation(summary = "온습도 기록 목록 조회", description = "최근 방 온습도 기록 10개를 조회합니다.")
    public CustomResponse<List<RoomConditionResponseDTO.RoomConditionViewDTO>> getRoomConditionPage(@PathVariable("childId") Long childId
    ,@RequestParam(value="page", defaultValue="0") int page, @AuthParent Parent parent) {
        List<RoomConditionResponseDTO.RoomConditionViewDTO> roomConditionViewList = roomConditionQueryService.getRoomConditionPage(childId, page, parent);
        return CustomResponse.onSuccess(roomConditionViewList);
    }

    @GetMapping("/graph1/{childId}")
    @Operation(summary = "1일 온습도 기록 그래프 조회", description = "1일 그래프를 위한 기록 조회")
    public CustomResponse<List<RoomConditionResponseDTO.RoomConditionGrpahDTO>>getRoomConditionGraphDay1(@PathVariable("childId") Long childId,
                                                                                                   @AuthParent Parent parent) {
        List<RoomConditionResponseDTO.RoomConditionGrpahDTO> RoomConditionViewList = roomConditionQueryService.getRoomConditionGraphDay1(childId, parent);
        return CustomResponse.onSuccess(RoomConditionViewList);
    }

    @GetMapping("/graph3/{childId}")
    @Operation(summary = "3일 온습도 기록 그래프 조회", description = "3일 그래프를 위한 기록 조회")
    public CustomResponse<List<RoomConditionResponseDTO.RoomConditionGrpahDTO>>getRoomConditionGraphDay3(@PathVariable("childId") Long childId,
                                                                                                   @AuthParent Parent parent) {
        List<RoomConditionResponseDTO.RoomConditionGrpahDTO> RoomConditionViewList = roomConditionQueryService.getRoomConditionGraphDay3(childId, parent);
        return CustomResponse.onSuccess(RoomConditionViewList);
    }

    @GetMapping("/graph7/{childId}")
    @Operation(summary = "7일 온습도 기록 그래프 조회", description = "7일 그래프를 위한 기록 조회")
    public CustomResponse<List<RoomConditionResponseDTO.RoomConditionGrpahDTO>>getRoomConditionGraphDay7(@PathVariable("childId") Long childId,
                                                                                                   @AuthParent Parent parent) {
        List<RoomConditionResponseDTO.RoomConditionGrpahDTO> RoomConditionViewList = roomConditionQueryService.getRoomConditionGraphDay7(childId, parent);
        return CustomResponse.onSuccess(RoomConditionViewList);
    }
}
