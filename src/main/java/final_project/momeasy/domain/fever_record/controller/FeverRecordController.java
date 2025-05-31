package final_project.momeasy.domain.fever_record.controller;

import final_project.momeasy.domain.fever_record.dto.FeverRecordResponseDTO;
import final_project.momeasy.domain.fever_record.service.FeverRecordQueryService;
import final_project.momeasy.global.apiPayload.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/feverRecords")
public class FeverRecordController {
    private final FeverRecordQueryService feverRecordQueryService;

    @GetMapping("/{childId}")
    public CustomResponse<FeverRecordResponseDTO.FeverRecordViewDTO> getFeverRecord(@PathVariable("childId") Long childId) {
        FeverRecordResponseDTO.FeverRecordViewDTO feverRecordViewDTO = feverRecordQueryService.getFeverRecord(childId);
        return CustomResponse.onSuccess(feverRecordViewDTO);
    }

    @GetMapping("/list/{childId}")
    public CustomResponse<List<FeverRecordResponseDTO.FeverRecordViewDTO>>getFeverRecordPage(@PathVariable("childId") Long childId,
         @RequestParam(value="page", defaultValue="0") int page) {
        List<FeverRecordResponseDTO.FeverRecordViewDTO> feverRecordViewList = feverRecordQueryService.getFeverRecordPage(childId,page);
        return CustomResponse.onSuccess(feverRecordViewList);
    }

    @GetMapping("/all/{childId}")
    public CustomResponse<List<FeverRecordResponseDTO.FeverRecordViewDTO>>getFeverRecordList(@PathVariable("childId") Long childId) {
        List<FeverRecordResponseDTO.FeverRecordViewDTO> feverRecordViewList = feverRecordQueryService.getFeverRecordList(childId);
        return CustomResponse.onSuccess(feverRecordViewList);
    }
}
