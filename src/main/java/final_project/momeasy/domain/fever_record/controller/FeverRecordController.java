package final_project.momeasy.domain.fever_record.controller;

import final_project.momeasy.domain.fever_record.dto.FeverRecordResponseDTO;
import final_project.momeasy.domain.fever_record.service.FeverRecordQueryService;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.security.annotation.AuthParent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feverRecords")
@Tag(name = "FeverRecord", description = "체온 기록 API")
public class FeverRecordController {
    private final FeverRecordQueryService feverRecordQueryService;

    @GetMapping("/{childId}")
    @Operation(summary = "최근 체온 기록 조회", description = "최근 체온 기록을 조회합니다.")
    public CustomResponse<FeverRecordResponseDTO.FeverRecordViewDTO> getFeverRecord(@PathVariable("childId") Long childId, @AuthParent Parent parent) {
        FeverRecordResponseDTO.FeverRecordViewDTO feverRecordViewDTO = feverRecordQueryService.getFeverRecord(childId, parent);
        return CustomResponse.onSuccess(feverRecordViewDTO);
    }

    @GetMapping("/recent/{childId}")
    @Operation(summary = "최근 발열 기록 조회", description = "최근 발열 기록을 조회합니다.")
    public CustomResponse<FeverRecordResponseDTO.FeverRecordViewDTO> getRecentHighFeverRecord(@PathVariable("childId") Long childId, @AuthParent Parent parent) {
        FeverRecordResponseDTO.FeverRecordViewDTO feverRecordViewDTO = feverRecordQueryService.getRecentHighFeverRecord(childId, parent);
        return CustomResponse.onSuccess(feverRecordViewDTO);
    }

    @GetMapping("/list/{childId}")
    @Operation(summary = "체온 기록 목록 조회", description = "최근 체온 기록 10개를 조회합니다.")
    public CustomResponse<List<FeverRecordResponseDTO.FeverRecordViewDTO>>getFeverRecordPage(@PathVariable("childId") Long childId,
         @RequestParam(value="page", defaultValue="0") int page, @AuthParent Parent parent) {
        List<FeverRecordResponseDTO.FeverRecordViewDTO> feverRecordViewList = feverRecordQueryService.getFeverRecordPage(childId,page, parent);
        return CustomResponse.onSuccess(feverRecordViewList);
    }

    @GetMapping("/graph1/{childId}")
    @Operation(summary = "1일 체온 기록 그래프 조회", description = "1일 그래프를 위한 기록 조회")
    public CustomResponse<List<FeverRecordResponseDTO.FeverRecordGraphDTO>>getFeverRecordGraphDay1(@PathVariable("childId") Long childId,
             @AuthParent Parent parent) {
        List<FeverRecordResponseDTO.FeverRecordGraphDTO> feverRecordViewList = feverRecordQueryService.getFeverRecordGraphDay1(childId, parent);
        return CustomResponse.onSuccess(feverRecordViewList);
    }

    @GetMapping("/graph3/{childId}")
    @Operation(summary = "3일 체온 기록 그래프 조회", description = "3일 그래프를 위한 기록 조회")
    public CustomResponse<List<FeverRecordResponseDTO.FeverRecordGraphDTO>>getFeverRecordGraphDay3(@PathVariable("childId") Long childId,
                @AuthParent Parent parent) {
        List<FeverRecordResponseDTO.FeverRecordGraphDTO> feverRecordViewList = feverRecordQueryService.getFeverRecordGraphDay3(childId, parent);
        return CustomResponse.onSuccess(feverRecordViewList);
    }

    @GetMapping("/graph7/{childId}")
    @Operation(summary = "7일 체온 기록 그래프 조회", description = "7일 그래프를 위한 기록 조회")
    public CustomResponse<List<FeverRecordResponseDTO.FeverRecordGraphDTO>>getFeverRecordGraphDay7(@PathVariable("childId") Long childId,
                @AuthParent Parent parent) {
        List<FeverRecordResponseDTO.FeverRecordGraphDTO> feverRecordViewList = feverRecordQueryService.getFeverRecordGraphDay7(childId, parent);
        return CustomResponse.onSuccess(feverRecordViewList);
    }
}