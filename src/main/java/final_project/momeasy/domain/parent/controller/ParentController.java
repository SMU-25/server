package final_project.momeasy.domain.parent.controller;

import final_project.momeasy.domain.parent.dto.request.ParentRequestDTO;
import final_project.momeasy.domain.parent.dto.response.ParentResponseDTO;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.parent.service.command.ParentCommandService;
import final_project.momeasy.domain.parent.service.query.ParentQueryService;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.security.annotation.AuthParent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Tag(name = "Parent", description = "부모(회원) API by 현빈")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/my")
public class ParentController {

    private final ParentCommandService parentCommandService;
    private final ParentQueryService parentQueryService;

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping
    public CustomResponse<String> deleteParent(@AuthParent Parent parent) {
        Long parentId = parent.getId();
        parentCommandService.deleteParent(parentId);
        return CustomResponse.onSuccess(HttpStatus.NO_CONTENT, "회원 탈퇴 완료");
    }

    @Operation(summary = "마이페이지 - 회원 정보 조회")
    @GetMapping
    public CustomResponse<ParentResponseDTO.ParentDetailResponseDTO> getParent(
            @AuthParent Parent parent) {
        Long parentId = parent.getId();
        return CustomResponse.onSuccess(parentQueryService.getParentDetail(parentId));
    }

    @Operation(summary = "마이페이지 - 회원 정보 수정")
    @PatchMapping
    public CustomResponse<String> updateParent(@AuthParent Parent parent, @RequestBody ParentRequestDTO.ParentUpdateRequestDTO dto) {
        Long parentId = parent.getId();
        parentCommandService.updateParent(parentId, dto);
        return CustomResponse.onSuccess(HttpStatus.CREATED, "회원 정보 수정 완료");
    }

    @Operation(summary = "마이페이지 - 프로필 사진 수정")
    @PatchMapping("/profile-image")
    public CustomResponse<?> updateProfileImage(
            @AuthParent Parent parent,
            @RequestPart MultipartFile profileImage
            ) throws IOException {
        String profileUrl = parentCommandService.updateProfileImage(parent.getId(), profileImage);
        return CustomResponse.onSuccess(profileUrl);
    }
}
