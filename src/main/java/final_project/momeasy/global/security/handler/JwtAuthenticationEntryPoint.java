package final_project.momeasy.global.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.apiPayload.code.GeneralErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.warn("[ JwtAuthenticationEntryPoint ] 인증되지 않은 사용자 요청");

        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        CustomResponse<Void> errorResponse = CustomResponse.onFailure(
                GeneralErrorCode.UNAUTHORIZED_401, null
        );

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
}
