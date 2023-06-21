package study.security.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import study.security.config.security.form.CustomAuthenticationProvider;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@EnableWebSecurity
@Order(1)
public class SecurityConfig {

    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;

    // 정적 파일에 대한 검사 Skip
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    // Custom 한 로그인 처리를 위해 AuthenticationProvider 등록
    // ref. https://www.baeldung.com/spring-deprecated-websecurityconfigureradapter
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(customAuthenticationProvider)
                .build();
    }

    @Bean
    public SecurityFilterChain formFilterChain(HttpSecurity http) throws Exception {
        http
                // AuthorizeRequests
                .authorizeRequests()
                .antMatchers("/", "/signup", "/signup-proc").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                // 로그인
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .authenticationDetailsSource(authenticationDetailsSource)
                .loginProcessingUrl("/login-proc")
                .permitAll()
                .and()
                // 로그아웃
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/")
                .deleteCookies()
                .deleteCookies("JSESSIONID");

        return http.build();
    }
}
