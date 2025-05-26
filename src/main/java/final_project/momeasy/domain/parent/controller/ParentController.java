package final_project.momeasy.domain.parent.controller;

import final_project.momeasy.domain.parent.dto.request.ParentRequestDTO;
import final_project.momeasy.domain.parent.dto.response.ParentResponseDTO;
import final_project.momeasy.domain.parent.service.command.ParentCommandService;
import final_project.momeasy.global.apiPayload.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ParentController {

    private final ParentCommandService parentCommandService;

    @PostMapping("/signup")
    public CustomResponse<ParentResponseDTO.ParentCreateResponseDTO> localSignUp(
            @RequestBody ParentRequestDTO.ParentCreateRequestDTO createDTO) {
        return CustomResponse.onSuccess(HttpStatus.CREATED, parentCommandService.createParent(createDTO));
    }
}
