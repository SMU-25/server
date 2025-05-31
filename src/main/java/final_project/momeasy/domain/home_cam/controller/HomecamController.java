package final_project.momeasy.domain.home_cam.controller;

import final_project.momeasy.domain.home_cam.dto.HomecamRequestDTO;
import final_project.momeasy.domain.home_cam.dto.HomecamResponseDTO;
import final_project.momeasy.domain.home_cam.service.HomecamQueryService;
import final_project.momeasy.domain.home_cam.service.HomecamService;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.security.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/homecams")
@Tag(name = "Homecam", description = "Homecam Controller")
public class HomecamController {
    // security 완성되면 @AuthenticatedMember 추가하기
    private final HomecamQueryService homecamQueryService;
    private final HomecamService homecamService;

    @GetMapping("/{homecamId}")
    @Operation(summary = "홈캠 1개 조회 API", description = "homecam 1개를 조회합니다.")
    public CustomResponse<HomecamResponseDTO.HomecamDTO> getHomecam(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("homecamId") long homecamId) {
        HomecamResponseDTO.HomecamDTO homecam = homecamQueryService.getHomecamById(homecamId,userDetails.getParent());
        return CustomResponse.onSuccess(homecam);
    }

    @GetMapping("/urls/{homecamId}")
    @Operation(summary = "홈캠 cctv URL 조회 API", description = "홈캠의 cctv URL을 조회합니다.")
    public CustomResponse<HomecamResponseDTO.HomecamVideoDTO> getHomecamVideo(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("homecamId") long homecamId) {
        HomecamResponseDTO.HomecamVideoDTO homecam = homecamQueryService.getHomecamVideoById(homecamId, userDetails.getParent());
        return CustomResponse.onSuccess(homecam);
    }

    @GetMapping("/list")
    @Operation(summary = "홈캠 전체 조회 API", description = "homecam을 전체 조회합니다.")
    public CustomResponse<List<HomecamResponseDTO.HomecamDTO>> getHomecams(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<HomecamResponseDTO.HomecamDTO> homecamList = homecamQueryService.getHomecamListByParent(userDetails.getParent());
        return CustomResponse.onSuccess(homecamList);
    }

    @DeleteMapping("/{homecamId}")
    @Operation(summary = "홈캠 삭제 API", description = "homecam을 1개 삭제합니다.")
    public CustomResponse<Void> deleteHomecam(@PathVariable("homecamId") long homecamId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        homecamService.deleteHomecam(homecamId,userDetails.getParent());
        return CustomResponse.onSuccess(null);
    }

    @PostMapping("/{childId}")
    @Operation(summary = "홈캠 생성 API", description = "홈캠을 생성합니다.")
    public CustomResponse<HomecamResponseDTO.HomecamDTO> createHomecam(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody HomecamRequestDTO.HomecamRegisterDTO homecamRegisterDTO, @PathVariable("childId") Long childId){
        HomecamResponseDTO.HomecamDTO homecam = homecamService.createHomecam(homecamRegisterDTO,userDetails.getParent(), childId);
        return CustomResponse.onSuccess(homecam);
    }
}
