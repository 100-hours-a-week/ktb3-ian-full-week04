package ktb3.full.community.common.annotation.resolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static ktb3.full.community.common.Constants.SESSION_ATTRIBUTE_NAME_LOGGED_IN_USER;

public class AuthenticationArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasParameterAnnotation = parameter.hasParameterAnnotation(Authentication.class);
        boolean isAssignableFrom = ktb3.full.community.dto.session.LoggedInUser.class.isAssignableFrom(parameter.getParameterType());

        return hasParameterAnnotation && isAssignableFrom;
    }

    @Nullable
    @Override
    public Object resolveArgument(MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);

        return session.getAttribute(SESSION_ATTRIBUTE_NAME_LOGGED_IN_USER);
    }
}
