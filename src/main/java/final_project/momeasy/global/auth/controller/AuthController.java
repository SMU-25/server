package final_project.momeasy.global.auth.controller;

import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.security.dto.request.LoginRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

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
