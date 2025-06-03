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
@Tag(name = "FeverRecord", description = "FeverRecord Controller")
public class FeverRecordController {
    private final FeverRecordQueryService feverRecordQueryService;

    @GetMapping("/{childId}")
    @Operation(summary = "최근 체온 기록 조회 API", description = "최근 체온 기록을 조회합니다.")
    public CustomResponse<FeverRecordResponseDTO.FeverRecordViewDTO> getFeverRecord(@PathVariable("childId") Long childId, @AuthParent Parent parent) {
        FeverRecordResponseDTO.FeverRecordViewDTO feverRecordViewDTO = feverRecordQueryService.getFeverRecord(childId, parent);
        return CustomResponse.onSuccess(feverRecordViewDTO);
    }

    @GetMapping("/list/{childId}")
    @Operation(summary = "체온 기록 조회 API", description = "최근 체온 기록 10개를 조회합니다.")
    public CustomResponse<List<FeverRecordResponseDTO.FeverRecordViewDTO>>getFeverRecordPage(@PathVariable("childId") Long childId,
         @RequestParam(value="page", defaultValue="0") int page, @AuthParent Parent parent) {
        List<FeverRecordResponseDTO.FeverRecordViewDTO> feverRecordViewList = feverRecordQueryService.getFeverRecordPage(childId,page, parent);
        return CustomResponse.onSuccess(feverRecordViewList);
    }

    @GetMapping("/all/{childId}")
    @Operation(summary = "전체 체온 기록 조회 API", description = "전체 체온 기록을 조회합니다.")
    public CustomResponse<List<FeverRecordResponseDTO.FeverRecordViewDTO>>getFeverRecordList(@PathVariable("childId") Long childId,
        @AuthParent Parent parent) {
        List<FeverRecordResponseDTO.FeverRecordViewDTO> feverRecordViewList = feverRecordQueryService.getFeverRecordList(childId, parent);
        return CustomResponse.onSuccess(feverRecordViewList);
    }
}
