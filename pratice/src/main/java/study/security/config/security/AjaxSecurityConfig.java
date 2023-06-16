package study.security.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import study.security.config.security.ajax.*;

@RequiredArgsConstructor
@EnableWebSecurity
@Order(0)
public class AjaxSecurityConfig {

    private final AjaxAuthenticationProvider ajaxAuthenticationProvider;

    // `HttpSecurity`에는 공유 객체를 저장하는 저장소가 있다.
    @Bean
    public AuthenticationManager ajaxAuthenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(ajaxAuthenticationProvider)
                .build();
    }

    @Bean
    public AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
        return new AjaxAuthenticationSuccessHandler();
    }

    @Bean
    public AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler() {
        return new AjaxAuthenticationFailureHandler();
    }

    @Bean
    public AjaxLoginProcessingFilter ajaxLoginProcessingFilter(HttpSecurity http) throws Exception {
        // "AjaxAuthenticationProvider"를 갖고 있는 "AuthenticationManager"를 정확히 지정해 주었다.
        AjaxLoginProcessingFilter filter = new AjaxLoginProcessingFilter(ajaxAuthenticationManager(http));

        // SuccessHandler, FailureHandler 적용
        filter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler());

        return filter;
    }

    @Bean
    public AjaxAccessDeniedHandler ajaxAccessDeniedHandler() {
        return new AjaxAccessDeniedHandler();
    }

    @Bean
    public SecurityFilterChain ajaxFilterChain(HttpSecurity http) throws Exception {
        http
                // "/api/**" 경로에만 적용
                .antMatcher("/api/**")
                // api 경로는 csrf 방어 disable
                .csrf().disable()
                // AuthorizeRequests
                .authorizeRequests()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/api/messages").hasRole("MANAGER")
                .anyRequest().authenticated()
                .and()
                // Ajax Login Filter 추가
                .addFilterBefore(ajaxLoginProcessingFilter(http), UsernamePasswordAuthenticationFilter.class)
                // Ajax 인가 에러 처리
                .exceptionHandling()
                .authenticationEntryPoint(new AjaxAuthenticationEntryPoint()) // 일반 객체를 넣어도 되고
                .accessDeniedHandler(ajaxAccessDeniedHandler()); // 빈을 만들어 넣어도 된다.

        return http.build();
    }
}
