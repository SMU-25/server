package final_project.momeasy.domain.room_condition.controller;

import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.room_condition.dto.RoomConditionResponseDTO;
import final_project.momeasy.domain.room_condition.service.RoomConditionQueryService;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.security.annotation.AuthParent;
import final_project.momeasy.global.validation.annoation.CheckCursor;
import final_project.momeasy.global.validation.annoation.CheckSize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@AllArgsConstructor
@RequestMapping("/api/rooms")
@Tag(name = "RoomCondition", description = "온습도 기록 API by 김준형")
public class RoomConditionController {
    private final RoomConditionQueryService roomConditionQueryService;

    @GetMapping("/{childId}")
    @Operation(summary = "최근 온습도 기록 조회", description = "최근 방 온습도 기록을 조회합니다. 홈 화면에서 최근 체온을 표시할 때 사용되는 API입니다.")
    public CustomResponse<RoomConditionResponseDTO.RoomConditionViewDTO> getRoomCondition(@PathVariable("childId") Long childId, @AuthParent Parent parent) {
        RoomConditionResponseDTO.RoomConditionViewDTO roomConditionViewDTO = roomConditionQueryService.getRoomCondition(childId, parent);
        return CustomResponse.onSuccess(roomConditionViewDTO);
    }

    @GetMapping("list/{childId}")
    @Parameter(name = "cursor", description = "데이터가 시작하는 부분을 표시합니다.", example = "0")
    @Parameter(name = "size", description = "size만큼 데이터를 가져옵니다.", example = "10")
    @Operation(summary = "온습도 기록 목록 조회", description = "최근 방 온습도 기록 10개를 조회합니다. 알림 화면에서 사용되는 API입니다.")
    public CustomResponse<RoomConditionResponseDTO.RoomConditionListViewDTO> getRoomConditionPage(@PathVariable("childId") Long childId
    , @RequestParam(name = "cursor") @CheckCursor Long cursor, @RequestParam(name = "size") @CheckSize Integer size, @AuthParent Parent parent) {
        RoomConditionResponseDTO.RoomConditionListViewDTO roomConditionViewList = roomConditionQueryService.getRoomConditionPage(childId, cursor, size, parent);
        return CustomResponse.onSuccess(roomConditionViewList);
    }

    @GetMapping("/graph1/{childId}")
    @Operation(summary = "1일 온습도 기록 그래프 조회", description = "1일 그래프를 위한 기록을 조회합니다. 홈캠에서 사용되는 API입니다." +
            "\n result는 시간 내림차순 형태입니다. 인덱스 0이 가장 오래된 오늘, 0~3시 데이터입니다.<br>"+
            "0~3시, 3~6시, 6~9시, ... ,21시~0시")
    public CustomResponse<List<RoomConditionResponseDTO.RoomConditionGrpahDTO>>getRoomConditionGraphDay1(@PathVariable("childId") Long childId,
                                                                                                   @AuthParent Parent parent) {
        List<RoomConditionResponseDTO.RoomConditionGrpahDTO> RoomConditionViewList = roomConditionQueryService.getRoomConditionGraphDay1(childId, parent);
        return CustomResponse.onSuccess(RoomConditionViewList);
    }

    @GetMapping("/graph3/{childId}")
    @Operation(summary = "3일 온습도 기록 그래프 조회", description = "3일 그래프를 위한 기록 조회을 조회합니다. 홈캠에서 사용되는 API입니다." +
            "\n result는 시간 내림차순 형태입니다. 인덱스 0이 가장 오래된 3일전, 새벽 데이터입니다.<br>"+
            "새벽(0~6시), 오전(6시~12시), 오후(12시~0시)")
    public CustomResponse<List<RoomConditionResponseDTO.RoomConditionGrpahDTO>>getRoomConditionGraphDay3(@PathVariable("childId") Long childId,
                                                                                                   @AuthParent Parent parent) {
        List<RoomConditionResponseDTO.RoomConditionGrpahDTO> RoomConditionViewList = roomConditionQueryService.getRoomConditionGraphDay3(childId, parent);
        return CustomResponse.onSuccess(RoomConditionViewList);
    }

    @GetMapping("/graph7/{childId}")
    @Operation(summary = "7일 온습도 기록 그래프 조회", description = "7일 그래프를 위한 기록 조회을 조회합니다. 홈캠에서 사용되는 API입니다." +
            "\n result는 시간 내림차순 형태입니다. 인덱스 0이 가장 오래된 일주일 전 데이터입니다.<br>"+
            "7일전, 6일전, 5일전, ... , 오늘")
    public CustomResponse<List<RoomConditionResponseDTO.RoomConditionGrpahDTO>>getRoomConditionGraphDay7(@PathVariable("childId") Long childId,
                                                                                                   @AuthParent Parent parent) {
        List<RoomConditionResponseDTO.RoomConditionGrpahDTO> RoomConditionViewList = roomConditionQueryService.getRoomConditionGraphDay7(childId, parent);
        return CustomResponse.onSuccess(RoomConditionViewList);
    }
}
