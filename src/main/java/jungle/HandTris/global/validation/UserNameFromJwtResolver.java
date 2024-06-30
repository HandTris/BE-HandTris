package jungle.HandTris.global.validation;

import jakarta.servlet.http.HttpServletRequest;
import jungle.HandTris.global.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class UserNameFromJwtResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private JWTUtil jwtUtil;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(UserNicknameFromJwt.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, org.springframework.web.bind.support.WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String token = jwtUtil.resolveAccessToken(request);
        return jwtUtil.getNickname(token);
    }
}
