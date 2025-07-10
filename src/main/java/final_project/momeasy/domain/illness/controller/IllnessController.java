package final_project.momeasy.domain.illness.controller;

import final_project.momeasy.domain.illness.dto.IllnessResponse;
import final_project.momeasy.domain.illness.service.IllnessService;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.security.annotation.AuthParent;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/illnesses")
public class IllnessController {

    private final IllnessService illnessService;

    @GetMapping
    @Operation(summary = "질환 목록 조회", description = "시스템에 등록된 모든 질환 항목을 조회합니다.")
    public ResponseEntity<CustomResponse<List<IllnessResponse>>> getAllIllnesses(@AuthParent Parent parent) {
        List<IllnessResponse> response = illnessService.getAllIllnesses();
        return ResponseEntity.ok(CustomResponse.onSuccess(response));
    }
}
