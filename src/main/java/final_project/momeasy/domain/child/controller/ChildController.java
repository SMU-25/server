package final_project.momeasy.domain.child.controller;

import final_project.momeasy.domain.child.dto.request.ChildRequestDTO;
import final_project.momeasy.domain.child.dto.response.ChildResponseDTO;
import final_project.momeasy.domain.child.service.command.ChildCommandService;
import final_project.momeasy.domain.child.service.query.ChildQueryService;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.security.annotation.AuthParent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/children")
public class ChildController {

    private final ChildCommandService childCommandService;
    private final ChildQueryService childQueryService;

    @PostMapping
    public CustomResponse<ChildResponseDTO.ChildCreateResponseDTO> createChild(@RequestBody ChildRequestDTO.ChildCreateRequestDTO createDTO, @AuthParent Parent parent) {
        return CustomResponse.onSuccess(
                HttpStatus.CREATED, childCommandService.createChild(createDTO, parent.getId()));
    }

    @DeleteMapping("/{childId}")
    public CustomResponse<String> deleteChild(@PathVariable Long childId, @AuthParent Parent parent) {
        childCommandService.deleteChild(childId, parent);
        return CustomResponse.onSuccess(HttpStatus.NO_CONTENT, "아이 삭제 완료");
    }

    @PatchMapping("/{childId}")
    public CustomResponse<String> updateChild(@PathVariable Long childId,
                                         @RequestBody ChildRequestDTO.ChildUpdateRequestDTO dto,
                                         @AuthParent Parent parent) {
        childCommandService.updateChild(childId, parent, dto);
        return CustomResponse.onSuccess(HttpStatus.OK, "아이 정보 수정 완료");
    }

    @GetMapping("/{childId}")
    public CustomResponse<ChildResponseDTO.ChildDetailResponseDTO> getChild(@AuthParent Parent parent, @PathVariable Long childId) {
        return CustomResponse.onSuccess(HttpStatus.OK, childQueryService.getChild(childId, parent));
    }

    @GetMapping
    public CustomResponse<List<ChildResponseDTO.ChildSimpleResponseDTO>> getChildren(@AuthParent Parent parent) {
        return CustomResponse.onSuccess(HttpStatus.OK, childQueryService.getChildren(parent));
    }

}
