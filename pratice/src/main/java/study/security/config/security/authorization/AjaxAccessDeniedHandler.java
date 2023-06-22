package study.security.config.security.authorization;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// `AccessDeniedHandler`: 이미 인증된 사용자가 자신의 권한 밖의 자원에 접근하려고 시도할 때 사용되는 인터페이스
public class AjaxAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.sendError(HttpStatus.FORBIDDEN.value(), "Access is denied");
    }
}
