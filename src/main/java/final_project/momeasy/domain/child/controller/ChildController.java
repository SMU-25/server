package final_project.momeasy.domain.child.controller;

import final_project.momeasy.domain.child.dto.request.ChildRequestDTO;
import final_project.momeasy.domain.child.dto.response.ChildResponseDTO;
import final_project.momeasy.domain.child.service.command.ChildCommandService;
import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.security.annotation.AuthParent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/children")
public class ChildController {

    private final ChildCommandService childCommandService;

    @PostMapping
    public CustomResponse<ChildResponseDTO.ChildCreateResponseDTO> createChild(@RequestBody ChildRequestDTO.ChildCreateRequestDTO createDTO, @AuthParent Parent parent) {
        return CustomResponse.onSuccess(
                HttpStatus.CREATED, childCommandService.createChild(createDTO, parent.getId()));
    }
}
