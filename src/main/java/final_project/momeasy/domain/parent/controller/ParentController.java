package final_project.momeasy.domain.parent.controller;

import final_project.momeasy.domain.parent.service.command.ParentCommandService;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.security.CustomUserDetails;
import final_project.momeasy.global.security.annotation.AuthParent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ParentController {

    private final ParentCommandService parentCommandService;

    @DeleteMapping
    public CustomResponse<String> deleteParent(@AuthParent CustomUserDetails customUserDetails) {
        Long parentId = customUserDetails.getParent().getId();
        parentCommandService.deleteParent(parentId);
        return CustomResponse.onSuccess(HttpStatus.NO_CONTENT, "회원 탈퇴 완료");
    }
}
