package study.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity
public class SecurityConfigV4_Authorization {

    // 테스트 용으로 InMemory 사용자를 생성
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        manager.createUser(User.builder().username("user").password("{noop}1111").roles("USER").build());
        manager.createUser(User.builder().username("sys").password("{noop}1111").roles("USER", "SYS").build());
        manager.createUser(User.builder().username("admin").password("{noop}1111").roles("USER", "SYS", "ADMIN").build());

        return manager;
    }

    /**
     * 인증 예외 조건 (AuthenticationException 발생 조건)
     * 1. 사용자가 아직 인증되지 않았음
     * 2. 인증에 실패함 (올바르지 않은 아이디나 패스워드)
     * -
     * 인가 예외 조건 (AccessDeniedException 발생 조건)
     * 1. 권한이 없는 페이지 접근
     * **********
     * authenticationEntryPoint : 인증 실패 시 처리
     * accessDeniedHandler : 인가 실패 시 처리
     * accessDeniedPage : 인가 실패 시 리디렉션 할 페이지
     *   - 특별히 처리할 내용 없으면 accessDeniedHandler 말고 이거 쓰는 것이 편하겠다.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("SecurityConfigV4_Authorization.filterChain");

        http
                .authorizeRequests()
                .antMatchers("/v4/user").hasRole("USER")
                .antMatchers("/v4/sys/admin").hasRole("ADMIN")
                .antMatchers("/v4/sys/**").access("hasRole('ADMIN') or hasRole('SYS')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
//                .successHandler(new AuthenticationSuccessHandler() {
//                    @Override
//                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                        RequestCache requestCache = new HttpSessionRequestCache();
//                        SavedRequest savedRequest = requestCache.getRequest(request, response);
//                        String redirectUrl = savedRequest.getRedirectUrl();
//                        response.sendRedirect(redirectUrl);
//                    }
//                })
                .and()
                .exceptionHandling()
                // authenticationEntryPoint는 설정하면 Spring Security가 제공하는 기본 login form을 반환하지 않아서 주석
//                .authenticationEntryPoint(new AuthenticationEntryPoint() {
//                    @Override
//                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//                        response.sendRedirect("/login");
//                    }
//                })
//                .accessDeniedHandler(new AccessDeniedHandler() {
//                    @Override
//                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//                        response.sendRedirect("/denied");
//                    }
//                });
                .accessDeniedPage("/denied");

        return http.build();
    }
}
