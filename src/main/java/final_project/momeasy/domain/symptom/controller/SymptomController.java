package final_project.momeasy.domain.symptom.controller;

import final_project.momeasy.domain.symptom.dto.SymptomResponse;
import final_project.momeasy.domain.symptom.service.SymptomService;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.security.annotation.AuthParent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Symptom", description = "증상 API by 정현")
@RequestMapping("/api/symptoms")
public class SymptomController {

    private final SymptomService symptomService;

    @GetMapping
    @Operation(summary = "증상 목록 조회", description = "시스템에 등록된 모든 증상 항목을 조회합니다.")
    public ResponseEntity<CustomResponse<List<SymptomResponse>>> getAllSymptoms(@AuthParent Parent parent) {
        List<SymptomResponse> response = symptomService.getAllSymptoms();
        return ResponseEntity.ok(CustomResponse.onSuccess(response));
    }
}
