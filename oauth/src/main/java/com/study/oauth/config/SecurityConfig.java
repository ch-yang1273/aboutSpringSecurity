package com.study.oauth.config;

import com.study.oauth.service.CustomOidcUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOidcUserService customOidcUserService;

    // 정적 파일에 대한 접근 검사 Skip
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                PathRequest.toStaticResources().atCommonLocations()
        );
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/user").access("hasAnyAuthority('SCOPE_profile_image','SCOPE_profile_nickname')")
                .antMatchers("/api/oidc").access("hasAnyAuthority('SCOPE_openid')")
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login(
                        oauth2 -> oauth2.userInfoEndpoint(
                                userInfoEndpointConfig -> userInfoEndpointConfig
//                                        .userService(customOAuth2UserService)
                                        .oidcUserService(customOidcUserService)
                        )
                )
                .logout()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
        ;
        return http.build();
    }
}
