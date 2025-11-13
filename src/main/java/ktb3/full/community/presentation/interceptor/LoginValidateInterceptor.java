package ktb3.full.community.presentation.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ktb3.full.community.common.exception.LoginRequiredException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static ktb3.full.community.common.Constants.SESSION_ATTRIBUTE_NAME_LOGGED_IN_USER;

@Component
public class LoginValidateInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SESSION_ATTRIBUTE_NAME_LOGGED_IN_USER) == null) {
            throw new LoginRequiredException();
        }

        return true;
    }
}
