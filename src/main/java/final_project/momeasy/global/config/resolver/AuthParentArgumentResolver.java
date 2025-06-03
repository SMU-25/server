package final_project.momeasy.global.config.resolver;

import final_project.momeasy.domain.parent.entity.Parent;
import final_project.momeasy.domain.parent.repository.ParentRepository;
import final_project.momeasy.global.auth.exception.AuthErrorCode;
import final_project.momeasy.global.auth.exception.AuthException;
import final_project.momeasy.global.security.annotation.AuthParent;
import final_project.momeasy.global.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthParentArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtUtil jwtUtil;
    private final ParentRepository parentRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthParent.class) && parameter.getParameterType().equals(Parent.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String header = webRequest.getHeader("Authorization");

        if(header != null && header.startsWith("Bearer ")) {
            String token = header.split(" ")[1];
            String email = jwtUtil.getEmail(token);
            return parentRepository.findByEmail(email).orElseThrow(()->new AuthException(AuthErrorCode.UNAUTHORIZED_ACCESS));
        }
        throw new AuthException(AuthErrorCode.UNAUTHORIZED_ACCESS);
    }
}
