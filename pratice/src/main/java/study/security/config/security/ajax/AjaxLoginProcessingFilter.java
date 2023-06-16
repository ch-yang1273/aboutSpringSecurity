package study.security.config.security.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;
import study.security.dto.MemberDto;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 인증 필터는 "AbstractAuthenticationProcessingFilter"를 상속해서 구현하면 되겠다.
// "UsernamePasswordAuthenticationFilter"도 이 추상 클래스를 상속했다.
@Slf4j
public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper mapper = new ObjectMapper();

    public AjaxLoginProcessingFilter(AuthenticationManager auth) {
        // 아래 URL로 접근을 하면 필터가 동작한다.
        super(new AntPathRequestMatcher("/api/login"));
        super.setAuthenticationManager(auth);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        if (!isAjax(request)) {
            log.error("Authentication is not supported");
            throw new IllegalStateException("Authentication is not supported");
        }

        MemberDto dto = mapper.readValue(request.getReader(), MemberDto.class);
        if (StringUtils.isEmpty(dto.getUsername()) || StringUtils.isEmpty(dto.getPassword())) {
            log.error("Username or Password is empty");
            throw new IllegalArgumentException("Username or Password is empty");
        }

        log.info("Authenticate AjaxAuthenticationToken");
        AjaxAuthenticationToken token = new AjaxAuthenticationToken(dto.getUsername(), dto.getPassword());
        return getAuthenticationManager().authenticate(token);
    }

    private boolean isAjax(HttpServletRequest request) {
        String isAjax = request.getHeader("X-Requested-with");
        if (isAjax != null) {
            return isAjax.equals("XMLHttpRequest");
        } else {
            return false;
        }
    }
}
