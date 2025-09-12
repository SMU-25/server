package final_project.momeasy.domain.child.controller;

import final_project.momeasy.domain.child.dto.request.ChildRequestDTO;
import final_project.momeasy.domain.child.dto.response.ChildResponseDTO;
import final_project.momeasy.domain.child.service.command.ChildCommandService;
import final_project.momeasy.domain.child.service.query.ChildQueryService;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.security.annotation.AuthParent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/children")
@Tag(name = "Child", description = "아이 API by 현빈")
public class ChildController {

    private final ChildCommandService childCommandService;
    private final ChildQueryService childQueryService;

    @Operation(summary = "아이 추가")
    @PostMapping
    public CustomResponse<ChildResponseDTO.ChildCreateResponseDTO> createChild(
            @RequestBody ChildRequestDTO.ChildCreateRequestDTO createDTO,
            @AuthParent Parent parent) {
        return CustomResponse.onSuccess(
                HttpStatus.CREATED, childCommandService.createChild(createDTO, parent.getId()));
    }

    @Operation(summary = "아이 삭제")
    @DeleteMapping("/{childId}")
    public CustomResponse<String> deleteChild(@PathVariable Long childId, @AuthParent Parent parent) {
        childCommandService.deleteChild(childId, parent);
        return CustomResponse.onSuccess(HttpStatus.NO_CONTENT, "아이 삭제 완료");
    }

    @Operation(summary = "아이 정보 수정")
    @PatchMapping("/{childId}")
    public CustomResponse<String> updateChild(
            @PathVariable Long childId,
            @RequestBody ChildRequestDTO.ChildUpdateRequestDTO updateDTO,
            @AuthParent Parent parent) {
        childCommandService.updateChild(childId, parent, updateDTO);
        return CustomResponse.onSuccess(HttpStatus.OK, "아이 정보 수정 완료");
    }

    @Operation(summary = "아이 정보 상세 조회")
    @GetMapping("/{childId}")
    public CustomResponse<ChildResponseDTO.ChildDetailResponseDTO> getChild(@AuthParent Parent parent, @PathVariable Long childId) {
        return CustomResponse.onSuccess(HttpStatus.OK, childQueryService.getChild(childId, parent));
    }

    @Operation(summary = "아이 목록 조회")
    @GetMapping
    public CustomResponse<List<ChildResponseDTO.ChildSimpleResponseDTO>> getChildren(@AuthParent Parent parent) {
        return CustomResponse.onSuccess(HttpStatus.OK, childQueryService.getChildren(parent));
    }

    @Operation(summary = "아이 프로필 사진 업로드")
    @PatchMapping("/{childId}/profile-image")
    public CustomResponse<?> uploadChildProfileImage(
            @PathVariable Long childId,
            @RequestPart MultipartFile profileImage,
            @AuthParent Parent parent
            ) throws IOException {
        String profileUrl = childCommandService.updateChildProfileImage(childId, parent, profileImage);
        return CustomResponse.onSuccess(HttpStatus.OK, profileUrl);
    }
}
