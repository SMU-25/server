package final_project.momeasy.global.auth.controller;

import final_project.momeasy.domain.parent.dto.request.ParentRequestDTO;
import final_project.momeasy.domain.parent.dto.response.ParentResponseDTO;
import final_project.momeasy.domain.parent.service.command.ParentCommandService;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.security.dto.request.LoginRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final ParentCommandService parentCommandService;

    @PostMapping("/signup")
    public CustomResponse<ParentResponseDTO.ParentCreateResponseDTO> localSignUp(
            @RequestBody ParentRequestDTO.ParentCreateRequestDTO createDTO) {
        return CustomResponse.onSuccess(HttpStatus.CREATED, parentCommandService.createParent(createDTO));
    }

    // Swagger test용 dummy controller
    @PostMapping("/login")
    public CustomResponse<?> localLogin(@RequestBody LoginRequestDTO loginDTO) {
        return null;
    }

    // Swagger test용 dummy controller
    @PostMapping("/logout")
    public CustomResponse<?> logout() {
        return null;
    }
}
