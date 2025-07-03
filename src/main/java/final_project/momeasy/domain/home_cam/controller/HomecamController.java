package final_project.momeasy.domain.home_cam.controller;

import final_project.momeasy.domain.home_cam.dto.HomecamRequestDTO;
import final_project.momeasy.domain.home_cam.dto.HomecamResponseDTO;
import final_project.momeasy.domain.home_cam.service.HomecamQueryService;
import final_project.momeasy.domain.home_cam.service.HomecamService;
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
@RequestMapping("/api/homecams")
@Tag(name = "Homecam", description = "홈캠 API")
public class HomecamController {
    private final HomecamQueryService homecamQueryService;
    private final HomecamService homecamService;

    @GetMapping("/{homecamId}")
    @Operation(summary = "홈캠 상세 조회", description = "homecam 1개를 조회합니다.")
    public CustomResponse<HomecamResponseDTO.HomecamDetailDTO> getHomecam(
            @AuthParent Parent parent,
            @PathVariable("homecamId") long homecamId) {
        HomecamResponseDTO.HomecamDetailDTO homecam = homecamQueryService.getHomecamById(homecamId,parent);
        return CustomResponse.onSuccess(homecam);
    }

    @GetMapping("/list")
    @Operation(summary = "홈캠 목록 조회", description = "homecam을 전체 조회합니다.")
    public CustomResponse<List<HomecamResponseDTO.HomecamDTO>> getHomecams(@AuthParent Parent parent) {
        List<HomecamResponseDTO.HomecamDTO> homecamList = homecamQueryService.getHomecamListByParent(parent);
        return CustomResponse.onSuccess(homecamList);
    }

    @DeleteMapping("/{homecamId}")
    @Operation(summary = "홈캠 삭제", description = "homecam을 1개 삭제합니다.")
    public CustomResponse<String> deleteHomecam(@PathVariable("homecamId") long homecamId, @AuthParent Parent parent) {
        homecamService.deleteHomecam(homecamId,parent);
        return CustomResponse.onSuccess(HttpStatus.NO_CONTENT,"홈캠 삭제 완료");
    }

    @PostMapping("/{childId}")
    @Operation(summary = "홈캠 생성", description = "홈캠을 생성합니다.")
    public CustomResponse<HomecamResponseDTO.HomecamDTO> createHomecam(
            @AuthParent Parent parent,
            @RequestBody HomecamRequestDTO.HomecamCreateDTO homecamRegisterDTO, @PathVariable("childId") Long childId){
        HomecamResponseDTO.HomecamDTO homecam = homecamService.createHomecam(homecamRegisterDTO,parent, childId);
        return CustomResponse.onSuccess(HttpStatus.CREATED,homecam);
    }

    @PatchMapping("/{childId}/{homecamId}")
    @Operation(summary = "홈캠 수정", description = "홈캠을 수정합니다.")
    public CustomResponse<String> updateHomecam(
            @AuthParent Parent parent,
            @RequestBody HomecamRequestDTO.HomecamUpdateDTO homecamRegisterDTO, @PathVariable("childId") long childId, @PathVariable("homecamId") long homecamId){
        homecamService.updateHomecam(homecamRegisterDTO,parent,childId,homecamId);
        return CustomResponse.onSuccess(HttpStatus.CREATED,"홈캠 수정 완료");
    }
}
