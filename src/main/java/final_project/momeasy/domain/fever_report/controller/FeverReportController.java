package final_project.momeasy.domain.fever_report.controller;

import final_project.momeasy.domain.fever_report.dto.FeverReportRequestDTO;
import final_project.momeasy.domain.fever_report.dto.FeverReportResponseDTO;
import final_project.momeasy.domain.fever_report.service.FeverReportQueryService;
import final_project.momeasy.domain.fever_report.service.FeverReportService;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.apiPayload.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
@Tag(name = "FeverReport", description = "FeverReport Controller")
public class FeverReportController {
    private final FeverReportService feverReportService;
    private final FeverReportQueryService feverReportQueryService;

    @GetMapping("/{childId}/{reportId}")
    @Operation(summary = "발열 리포트 1개 조회 API", description = "발열 리포트를 1개 조회합니다.")
    public CustomResponse<FeverReportResponseDTO.FeverReportViewDTO> getFeverReport(@AuthenticationPrincipal Parent parent,
        @PathVariable("childId") long childId, @PathVariable("reportId") long reportId) {
        FeverReportResponseDTO.FeverReportViewDTO feverReportViewDTO = feverReportQueryService.getFeverReport(parent, childId, reportId);
        return CustomResponse.onSuccess(feverReportViewDTO);
    }

    @GetMapping("/{childId}/list")
    @Operation(summary = "발열 리포트 조회 API", description = "최근 발열 리포트 10개를 조회합니다.")
    public CustomResponse<List<FeverReportResponseDTO.FeverReportViewDTO>> getFeverReports(@AuthenticationPrincipal Parent parent,
         @PathVariable("childId") long childId, @RequestParam(value="page", defaultValue="0") int page) {
        List<FeverReportResponseDTO.FeverReportViewDTO> feverReportViewDTOList = feverReportQueryService.getFeverReports(parent,childId,page);
        return CustomResponse.onSuccess(feverReportViewDTOList);
    }

    @DeleteMapping("/{childId}/{reportId}")
    @Operation(summary = "발열 리포트 삭제 API", description = "발열 리포트를 삭제합니다.")
    public CustomResponse<Void> deleteFeverReport(@AuthenticationPrincipal Parent parent,
     @PathVariable("childId") long childId, @PathVariable("reportId") long reportId){
        feverReportService.deleteFeverReport(parent,reportId,childId);
        return CustomResponse.onSuccess(null);
    }

    @PostMapping("/{childId}")
    @Operation(summary = "발열 리포트 생성 API", description = "발열 리포트를 생성합니다.")
    public CustomResponse<FeverReportResponseDTO.FeverReportViewDTO> createFeverReport(@AuthenticationPrincipal Parent parent,
    @RequestBody FeverReportRequestDTO.FeverReportCreateDTO feverReportRequestDTO, @PathVariable("childId") long childId) {
        FeverReportResponseDTO.FeverReportViewDTO feverReportViewDTO = feverReportService.createFeverReport(parent, feverReportRequestDTO, childId);
        return CustomResponse.onSuccess(feverReportViewDTO);
    }
}
