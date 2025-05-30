package final_project.momeasy.global.security.handler;

import final_project.momeasy.global.apiPayload.CustomResponse;
import final_project.momeasy.global.apiPayload.code.GeneralErrorCode;
import final_project.momeasy.global.util.ResponseUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        CustomResponse<Void> errorResponse = CustomResponse.onFailure(
                GeneralErrorCode.FORBIDDEN_403, null);

        ResponseUtil.writeJsonResponse(response, errorResponse, HttpStatus.FORBIDDEN);
    }
}
