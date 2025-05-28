package final_project.momeasy.domain.home_cam.controller;

import final_project.momeasy.domain.home_cam.dto.HomecamRequestDTO;
import final_project.momeasy.domain.home_cam.dto.HomecamResponseDTO;
import final_project.momeasy.domain.home_cam.service.HomecamQueryService;
import final_project.momeasy.domain.home_cam.service.HomecamQueryServiceImpl;
import final_project.momeasy.domain.home_cam.service.HomecamService;
import final_project.momeasy.domain.home_cam.service.HomecamServiceImpl;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.apiPayload.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/homecams")
public class HomecamController {
    // security 완성되면 @AuthenticatedMember 추가하기
    private final HomecamQueryService homecamQueryService;
    private final HomecamService homecamService;

    @GetMapping("/{homecamId}")
    public CustomResponse<HomecamResponseDTO.HomecamDTO> getHomecam(
            @AuthenticationPrincipal Parent parent,
            @PathVariable("homecamId") long homecamId) {
        HomecamResponseDTO.HomecamDTO homecam = homecamQueryService.getHomecamById(homecamId,parent);
        return CustomResponse.onSuccess(homecam);
    }

    @GetMapping("/urls/{homecamId}")
    public CustomResponse<HomecamResponseDTO.HomecamVideoDTO> getHomecamVideo(
            @AuthenticationPrincipal Parent parent,
            @PathVariable("homecamId") long homecamId) {
        HomecamResponseDTO.HomecamVideoDTO homecam = homecamQueryService.getHomecamVideoById(homecamId, parent);
        return CustomResponse.onSuccess(homecam);
    }

    @GetMapping("/list")
    public CustomResponse<List<HomecamResponseDTO.HomecamDTO>> getHomecams(@AuthenticationPrincipal Parent parent) {
        List<HomecamResponseDTO.HomecamDTO> homecamList = homecamQueryService.getHomecamListByParent(parent);
        return CustomResponse.onSuccess(homecamList);
    }

    @DeleteMapping("/{homecamId}")
    public CustomResponse<Void> deleteHomecam(@PathVariable("homecamId") long homecamId, @AuthenticationPrincipal Parent parent) {
        homecamService.deleteHomecam(homecamId,parent);
        return CustomResponse.onSuccess(null);
    }

    @PostMapping("/{childId}")
    public CustomResponse<HomecamResponseDTO.HomecamDTO> createHomecam(
            @AuthenticationPrincipal Parent parent,
            @RequestBody HomecamRequestDTO.HomecamRegisterDTO homecamRegisterDTO, @PathVariable("childId") Long childId){
        HomecamResponseDTO.HomecamDTO homecam = homecamService.createHomecam(homecamRegisterDTO,parent, childId);
        return CustomResponse.onSuccess(homecam);
    }
}
