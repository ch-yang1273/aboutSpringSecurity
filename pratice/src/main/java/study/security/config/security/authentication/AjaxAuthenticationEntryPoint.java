package study.security.config.security.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// `AuthenticationEntryPoint`: 인증되지 않은 사용자가 보호된 자원에 접근하려고 할 때 사용되는 인터페이스
public class AjaxAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.sendError(HttpStatus.UNAUTHORIZED.value(), "UnAuthorized");
    }
}
