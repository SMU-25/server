package final_project.momeasy.domain.fever_record.controller;

import final_project.momeasy.domain.fever_record.dto.FeverRecordResponseDTO;
import final_project.momeasy.domain.fever_record.service.FeverRecordQueryService;
import final_project.momeasy.global.apiPayload.CustomResponse;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feverRecord")
public class FeverRecordController {
    private final FeverRecordQueryService feverRecordQueryService;

    @GetMapping("/{child_id}")
    public CustomResponse<FeverRecordResponseDTO.FeverRecordViewDTO> getFeverRecord(@PathVariable("child_id") Long childId) {
        FeverRecordResponseDTO.FeverRecordViewDTO feverRecordViewDTO = feverRecordQueryService.getFeverRecord(childId);
        return CustomResponse.onSuccess(feverRecordViewDTO);
    }

    @GetMapping("/list/{child_id}")
    public CustomResponse<List<FeverRecordResponseDTO.FeverRecordViewDTO>>getFeverRecordPage(@PathVariable("child_id") Long childId,
         @RequestParam(value="page", defaultValue="0") int page) {
        List<FeverRecordResponseDTO.FeverRecordViewDTO> feverRecordViewList = feverRecordQueryService.getFeverRecordPage(childId,page);
        return CustomResponse.onSuccess(feverRecordViewList);
    }

    @GetMapping("/all/{child_id}")
    public CustomResponse<List<FeverRecordResponseDTO.FeverRecordViewDTO>>getFeverRecordList(@PathVariable("child_id") Long childId) {
        List<FeverRecordResponseDTO.FeverRecordViewDTO> feverRecordViewList = feverRecordQueryService.getFeverRecordList(childId);
        return CustomResponse.onSuccess(feverRecordViewList);
    }
}
