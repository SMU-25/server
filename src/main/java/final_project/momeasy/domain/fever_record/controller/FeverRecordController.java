package final_project.momeasy.domain.fever_record.controller;

import final_project.momeasy.domain.fever_record.dto.FeverRecordResponseDTO;
import final_project.momeasy.domain.fever_record.service.FeverRecordQueryService;
import final_project.momeasy.domain.fever_record.service.FeverRecordCommandService;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.security.annotation.AuthParent;
import final_project.momeasy.global.validation.annoation.CheckCursor;
import final_project.momeasy.global.validation.annoation.CheckSize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/feverRecords")
@Tag(name = "FeverRecord", description = "체온 기록 API by 김준형")
public class FeverRecordController {
    private final FeverRecordQueryService feverRecordQueryService;
    private final FeverRecordCommandService feverRecordCommandService;

    @GetMapping("/{childId}")
    @Operation(summary = "최근 체온 기록 조회", description = "홈 화면에서 최근 체온을 표시할 때 사용되는 API입니다.")
    public CustomResponse<FeverRecordResponseDTO.FeverRecordStateViewDTO> getFeverRecord(
            @PathVariable("childId") Long childId, @AuthParent Parent parent) {
        FeverRecordResponseDTO.FeverRecordStateViewDTO feverRecordViewDTO =
                feverRecordQueryService.getFeverRecord(childId, parent);
        return CustomResponse.onSuccess(feverRecordViewDTO);
    }

    @GetMapping("/recent/{childId}")
    @Operation(summary = "최근 발열 기록 조회", description = "리포트 생성 화면에서 외출 기록-최근 발열에 사용되는 API입니다.")
    public CustomResponse<FeverRecordResponseDTO.FeverRecordViewDTO> getRecentHighFeverRecord(
            @PathVariable("childId") Long childId, @AuthParent Parent parent) {
        FeverRecordResponseDTO.FeverRecordViewDTO feverRecordViewDTO =
                feverRecordQueryService.getRecentHighFeverRecord(childId, parent);
        return CustomResponse.onSuccess(feverRecordViewDTO);
    }

    @GetMapping("/list/{childId}")
    @Operation(summary = "체온 기록 목록 조회", description = "최근 체온 기록 목록을 조회합니다. 알림 화면에서 사용되는 API입니다.")
    @Parameter(name = "cursor", description = "데이터가 시작하는 부분을 표시합니다. 0부터 시작합니다.", example = "0")
    @Parameter(name = "size", description = "size만큼 데이터를 가져옵니다.", example = "10")
    public CustomResponse<FeverRecordResponseDTO.FeverRecordListViewDTO> getFeverRecordPage(
            @PathVariable("childId") Long childId,
            @RequestParam(name = "cursor") @CheckCursor Long cursor,
            @RequestParam(name = "size") @CheckSize Integer size,
            @AuthParent Parent parent) {
        FeverRecordResponseDTO.FeverRecordListViewDTO feverRecordViewList =
                feverRecordQueryService.getFeverRecordPage(childId, cursor, size, parent);
        return CustomResponse.onSuccess(feverRecordViewList);
    }

    @GetMapping("/graph1/{childId}")
    @Operation(summary = "1일 체온 기록 그래프 조회", description = "1일 그래프를 위한 기록을 조회합니다. 홈캠에서 사용되는 API입니다." +
            "\n result는 시간 내림차순 형태입니다. 인덱스 0이 가장 오래된 오늘, 0~3시 데이터입니다.<br>"+
            "0~3시, 3~6시, 6~9시, ... ,21시~0시")
    public CustomResponse<List<FeverRecordResponseDTO.FeverRecordGraphDTO>> getFeverRecordGraphDay1(
            @PathVariable("childId") Long childId, @AuthParent Parent parent) {
        List<FeverRecordResponseDTO.FeverRecordGraphDTO> feverRecordViewList =
                feverRecordQueryService.getFeverRecordGraphDay1(childId, parent);
        return CustomResponse.onSuccess(feverRecordViewList);
    }

    @GetMapping("/graph3/{childId}")
    @Operation(summary = "3일 체온 기록 그래프 조회", description = "3일 그래프를 위한 기록 조회을 조회합니다. 홈캠에서 사용되는 API입니다." +
            "\n result는 시간 내림차순 형태입니다. 인덱스 0이 가장 오래된 3일전, 새벽 데이터입니다.<br>"+
            "새벽(0~6시), 오전(6시~12시), 오후(12시~0시)")
    public CustomResponse<List<FeverRecordResponseDTO.FeverRecordGraphDTO>> getFeverRecordGraphDay3(
            @PathVariable("childId") Long childId, @AuthParent Parent parent) {
        List<FeverRecordResponseDTO.FeverRecordGraphDTO> feverRecordViewList =
                feverRecordQueryService.getFeverRecordGraphDay3(childId, parent);
        return CustomResponse.onSuccess(feverRecordViewList);
    }

    @GetMapping("/graph7/{childId}")
    @Operation(summary = "7일 체온 기록 그래프 조회", description = "7일 그래프를 위한 기록 조회을 조회합니다. 홈캠에서 사용되는 API입니다." +
            "\n result는 시간 내림차순 형태입니다. 인덱스 0이 가장 오래된 일주일 전 데이터입니다.<br>"+
            "7일전, 6일전, 5일전, ... , 오늘"
    )
    public CustomResponse<List<FeverRecordResponseDTO.FeverRecordGraphDTO>> getFeverRecordGraphDay7(
            @PathVariable("childId") Long childId, @AuthParent Parent parent) {
        List<FeverRecordResponseDTO.FeverRecordGraphDTO> feverRecordViewList =
                feverRecordQueryService.getFeverRecordGraphDay7(childId, parent);
        return CustomResponse.onSuccess(feverRecordViewList);
    }

    @PostMapping("/{childId}")
    @Operation(summary = "체온 기록 저장", description = "센서에서 전송된 아기 체온 기록을 저장합니다.")
    public CustomResponse<Void> saveFeverRecord(
            @PathVariable("childId") Long childId,
            @RequestParam("temperature") Float temperature,
            @AuthParent Parent parent
    ) {
        feverRecordCommandService.saveFeverRecord(childId, parent, temperature);
        return CustomResponse.onSuccess(null);
    }
}
