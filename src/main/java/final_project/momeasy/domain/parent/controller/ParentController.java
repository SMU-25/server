package final_project.momeasy.domain.parent.controller;

import final_project.momeasy.domain.parent.dto.request.ParentRequestDTO;
import final_project.momeasy.domain.parent.dto.response.ParentResponseDTO;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.parent.service.command.ParentCommandService;
import final_project.momeasy.domain.parent.service.query.ParentQueryService;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.security.annotation.AuthParent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/my")
public class ParentController {

    private final ParentCommandService parentCommandService;
    private final ParentQueryService parentQueryService;

    @DeleteMapping
    public CustomResponse<String> deleteParent(@AuthParent Parent parent) {
        Long parentId = parent.getId();
        parentCommandService.deleteParent(parentId);
        return CustomResponse.onSuccess(HttpStatus.NO_CONTENT, "회원 탈퇴 완료");
    }

    @GetMapping
    public CustomResponse<ParentResponseDTO.ParentDetailResponseDTO> getParent(
            @AuthParent Parent parent) {
        Long parentId = parent.getId();
        return CustomResponse.onSuccess(parentQueryService.getParentDetail(parentId));
    }

    @PatchMapping
    public CustomResponse<String> updateParent(@AuthParent Parent parent, @RequestBody ParentRequestDTO.ParentUpdateRequestDTO dto) {
        Long parentId = parent.getId();
        parentCommandService.updateParent(parentId, dto);
        return CustomResponse.onSuccess(HttpStatus.CREATED, "회원 정보 수정 완료");
    }


}
