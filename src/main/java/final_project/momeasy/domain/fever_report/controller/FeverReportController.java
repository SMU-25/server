package final_project.momeasy.domain.fever_report.controller;

import final_project.momeasy.domain.fever_report.dto.FeverReportRequestDTO;
import final_project.momeasy.domain.fever_report.dto.FeverReportResponseDTO;
import final_project.momeasy.domain.fever_report.service.FeverReportQueryService;
import final_project.momeasy.domain.fever_report.service.FeverReportService;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.security.annotation.AuthParent;
import final_project.momeasy.global.validation.annoation.CheckCursor;
import final_project.momeasy.global.validation.annoation.CheckSize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/api/reports")
@Tag(name = "FeverReport", description = "발열 리포트 API by 김준형")
public class FeverReportController {
    private final FeverReportService feverReportService;
    private final FeverReportQueryService feverReportQueryService;

    @GetMapping("/{reportId}")
    @Operation(summary = "발열 리포트 상세 조회", description = "발열 리포트를 1개 조회합니다.")
    public CustomResponse<FeverReportResponseDTO.FeverReportDetailViewDTO> getFeverReport(@AuthParent Parent parent,
        @PathVariable("reportId") long reportId) {
        FeverReportResponseDTO.FeverReportDetailViewDTO feverReportDetailViewDTO = feverReportQueryService.getFeverReport(parent, reportId);
        return CustomResponse.onSuccess(feverReportDetailViewDTO);
    }

    @GetMapping("/list/{childId}")
    @Operation(summary = "발열 리포트 목록 조회", description = "발열 리포트 10개를 조회합니다.")
    @Parameter(name = "cursor", description = "데이터가 시작하는 부분을 표시합니다.", example = "0")
    @Parameter(name = "size", description = "size만큼 데이터를 가져옵니다.", example = "10")
    public CustomResponse<FeverReportResponseDTO.FeverReportListViewDTO> getFeverReportPage(@AuthParent Parent parent,
  @PathVariable("childId") long childId, @RequestParam(name = "cursor") @CheckCursor Long cursor, @RequestParam(name = "size") @CheckSize Integer size) {
        FeverReportResponseDTO.FeverReportListViewDTO feverReportViewDTOList = feverReportQueryService.getFeverReports(parent,cursor, size,childId);
        return CustomResponse.onSuccess(feverReportViewDTOList);
    }

    @GetMapping("/all/{childId}")
    @Operation(summary = "발열 리포트 전체 조회", description = "전체 발열 리포트를 조회합니다.")
    public CustomResponse<List<FeverReportResponseDTO.FeverReportViewDTO>> getFeverReportList(@AuthParent Parent parent,
       @PathVariable("childId") long childId) {
        List<FeverReportResponseDTO.FeverReportViewDTO> feverReportViewDTOList = feverReportQueryService.getFeverReportList(parent,childId);
        return CustomResponse.onSuccess(feverReportViewDTOList);
    }

    @DeleteMapping("/{reportId}")
    @Operation(summary = "발열 리포트 삭제", description = "발열 리포트를 삭제합니다.")
    public CustomResponse<String> deleteFeverReport(@AuthParent Parent parent,
     @PathVariable("reportId") long reportId){
        feverReportService.deleteFeverReport(parent,reportId);
        return CustomResponse.onSuccess(HttpStatus.NO_CONTENT,"발열 리포트 삭제 완료");
    }

    @PostMapping("/{childId}")
    @Operation(summary = "발열 리포트 생성", description = "발열 리포트를 생성합니다. 리포트 생성 화면에서 사용되는 API 입니다.<br>"+
    "symptoms:[발열, 구토, 경련, 코피, 설사, 피부_발진, 실신, 호흡_곤란, 기침, 콧물, 황달, 해당_없음]")
    public CustomResponse<FeverReportResponseDTO.FeverReportCreateDTO> createFeverReport(@AuthParent Parent parent,
    @RequestBody FeverReportRequestDTO.FeverReportCreateDTO feverReportRequestDTO, @PathVariable("childId") long childId) {
        FeverReportResponseDTO.FeverReportCreateDTO feverReportViewDTO = feverReportService.createFeverReport(parent, feverReportRequestDTO, childId);
        return CustomResponse.onSuccess(HttpStatus.CREATED,feverReportViewDTO);
    }

    @PatchMapping("/{reportId}")
    @Operation(summary = "발열 리포트 수정", description = "발열 리포트를 수정합니다. 리포트 수정 화면에서 사용되는 API 입니다.<br>"+
            "symptoms:[발열, 구토, 경련, 코피, 설사, 피부_발진, 실신, 호흡_곤란, 기침, 콧물, 황달, 해당_없음]")
    public CustomResponse<String> updateFeverReport(@AuthParent Parent parent,
   @RequestBody FeverReportRequestDTO.FeverReportUpdateDTO feverReportRequestDTO, @PathVariable("reportId") long reportId) {
        feverReportService.updateFeverReport(parent,reportId, feverReportRequestDTO);
        return CustomResponse.onSuccess("발열 리포트 수정 완료");
    }
}
