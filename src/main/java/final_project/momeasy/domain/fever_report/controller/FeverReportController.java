package final_project.momeasy.domain.fever_report.controller;

import final_project.momeasy.domain.fever_report.dto.FeverReportRequestDTO;
import final_project.momeasy.domain.fever_report.dto.FeverReportResponseDTO;
import final_project.momeasy.domain.fever_report.service.FeverReportQueryService;
import final_project.momeasy.domain.fever_report.service.FeverReportService;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.security.annotation.AuthParent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reports")
@Tag(name = "FeverReport", description = "발열 리포트 API")
public class FeverReportController {
    private final FeverReportService feverReportService;
    private final FeverReportQueryService feverReportQueryService;

    @GetMapping("/{childId}/{reportId}")
    @Operation(summary = "발열 리포트 상세 조회", description = "발열 리포트를 1개 조회합니다.")
    public CustomResponse<FeverReportResponseDTO.FeverReportViewDTO> getFeverReport(@AuthParent Parent parent,
        @PathVariable("childId") long childId, @PathVariable("reportId") long reportId) {
        FeverReportResponseDTO.FeverReportViewDTO feverReportViewDTO = feverReportQueryService.getFeverReport(parent, childId, reportId);
        return CustomResponse.onSuccess(feverReportViewDTO);
    }

    @GetMapping("/list/{childId}")
    @Operation(summary = "발열 리포트 목록 조회", description = "최근 발열 리포트 10개를 조회합니다.")
    public CustomResponse<List<FeverReportResponseDTO.FeverReportViewDTO>> getFeverReportPage(@AuthParent Parent parent,
         @PathVariable("childId") long childId, @RequestParam(value="page", defaultValue="0") int page) {
        List<FeverReportResponseDTO.FeverReportViewDTO> feverReportViewDTOList = feverReportQueryService.getFeverReports(parent,childId,page);
        return CustomResponse.onSuccess(feverReportViewDTOList);
    }

    @GetMapping("/all/{childId}")
    @Operation(summary = "발열 리포트 전체 조회", description = "전체 발열 리포트를 조회합니다.")
    public CustomResponse<List<FeverReportResponseDTO.FeverReportViewDTO>> getFeverReportList(@AuthParent Parent parent,
       @PathVariable("childId") long childId) {
        List<FeverReportResponseDTO.FeverReportViewDTO> feverReportViewDTOList = feverReportQueryService.getFeverReportList(parent,childId);
        return CustomResponse.onSuccess(feverReportViewDTOList);
    }

    @DeleteMapping("/{childId}/{reportId}")
    @Operation(summary = "발열 리포트 삭제", description = "발열 리포트를 삭제합니다.")
    public CustomResponse<String> deleteFeverReport(@AuthParent Parent parent,
     @PathVariable("childId") long childId, @PathVariable("reportId") long reportId){
        feverReportService.deleteFeverReport(parent,reportId,childId);
        return CustomResponse.onSuccess(HttpStatus.NO_CONTENT,"아이 삭제 완료");
    }

    @PostMapping("/{childId}")
    @Operation(summary = "발열 리포트 생성", description = "발열 리포트를 생성합니다.")
    public CustomResponse<FeverReportResponseDTO.FeverReportViewDTO> createFeverReport(@AuthParent Parent parent,
    @RequestBody FeverReportRequestDTO.FeverReportCreateDTO feverReportRequestDTO, @PathVariable("childId") long childId) {
        FeverReportResponseDTO.FeverReportViewDTO feverReportViewDTO = feverReportService.createFeverReport(parent, feverReportRequestDTO, childId);
        return CustomResponse.onSuccess(HttpStatus.CREATED,feverReportViewDTO);
    }
}
