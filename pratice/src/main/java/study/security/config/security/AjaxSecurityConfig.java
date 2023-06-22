package study.security.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import study.security.config.security.authentication.AuthenticationBeans;
import study.security.config.security.authorization.AjaxAccessDeniedHandler;
import study.security.config.security.authorization.AuthorizationBeans;

@RequiredArgsConstructor
@EnableWebSecurity
@Order(0)
public class AjaxSecurityConfig {

    private final AuthenticationBeans authenticationBeans;
    private final AuthorizationBeans authorizationBeans;

    /**
     * `CustomFilterSecurityInterceptor`(인가 필터)를 이미 통과했으면,기존 `FilterSecurityInterceptor`는 무시된다.
     * *
     * FilterSecurityInterceptor.Invoke() 내용 중...
     * filter already applied to this request and user wants us to observe
     * once-per-request handling, so don't re-do security checking
     */
    @Bean
    public SecurityFilterChain ajaxFilterChain(HttpSecurity http) throws Exception {
        http
                // "/api/**" 경로에만 적용
                .antMatcher("/api/**")

                // csrf 방어 disable
                .csrf().disable()

                // AuthorizeRequests (이 설정들은 동작하지 않는다. 위의 설명 참고)
                .authorizeRequests()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/messages").hasRole("MANAGER")
                .anyRequest().authenticated()
                .and()

                // Ajax Login 인증 필터 추가
                .addFilterBefore(authenticationBeans.loginProcessingFilter(http), UsernamePasswordAuthenticationFilter.class)

                // Custom 인가 필터 추가
                .addFilterBefore(authorizationBeans.filterSecurityInterceptor(http), FilterSecurityInterceptor.class)

                // Ajax 인가 에러 처리
                .exceptionHandling()
                .authenticationEntryPoint(authenticationBeans.authenticationEntryPoint())
                .accessDeniedHandler(authorizationBeans.accessDeniedHandler()) // 빈을 만들어 넣어도 된다.
        ;
        return http.build();
    }
}
