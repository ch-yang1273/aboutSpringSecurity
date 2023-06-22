package study.security.config.security.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.AuthenticationEntryPoint;

@RequiredArgsConstructor
@Configuration
public class AuthenticationBeans {

    private final AjaxAuthenticationProvider ajaxAuthenticationProvider;

    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new AjaxAuthenticationEntryPoint();
    }

    // `HttpSecurity`에는 공유 객체를 저장하는 저장소가 있다.
    @Bean
    public AuthenticationManager ajaxAuthenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(ajaxAuthenticationProvider)
                .build();
    }

    @Bean
    public AjaxLoginProcessingFilter loginProcessingFilter(HttpSecurity http) throws Exception {
        // "AjaxAuthenticationProvider"를 갖고 있는 "AuthenticationManager"를 정확히 지정해 주었다.
        AjaxLoginProcessingFilter filter = new AjaxLoginProcessingFilter(ajaxAuthenticationManager(http));

        // SuccessHandler, FailureHandler 적용
        filter.setAuthenticationSuccessHandler(new AjaxAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(new AjaxAuthenticationFailureHandler());

        return filter;
    }
}
