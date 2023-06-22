package study.security.config.security;

import lombok.RequiredArgsConstructor;
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
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import study.security.config.security.authentication.AuthenticationBeans;
import study.security.config.security.authorization.AuthorizationBeans;
import study.security.config.security.form.CustomAuthenticationProvider;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@EnableWebSecurity
@Order(0)
public class SecurityConfig {

    private final AuthenticationBeans authenticationBeans;
    private final AuthorizationBeans authorizationBeans;

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
                // csrf 방어 disable
                .csrf().disable()

                // AuthorizeRequests (이 설정들은 동작하지 않는다. 위의 설명 참고)
                .authorizeRequests()
                .antMatchers("/mypage").hasRole("USER")
                .anyRequest().authenticated()
                .and()

                // Ajax Login 인증 필터 추가
                .addFilterBefore(authenticationBeans.loginProcessingFilter(http), UsernamePasswordAuthenticationFilter.class)

                // Custom 인가 필터 추가
                .addFilterBefore(authorizationBeans.filterSecurityInterceptor(http), FilterSecurityInterceptor.class)

                // Ajax 인가 에러 처리
                .exceptionHandling()
//                .authenticationEntryPoint(authenticationBeans.authenticationEntryPoint())
//                .accessDeniedHandler(authorizationBeans.accessDeniedHandler()) // 빈을 만들어 넣어도 된다.
                .and()

                // form 로그인 페이지
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
                // form 로그아웃 페이지
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/")
                .deleteCookies()
                .deleteCookies("JSESSIONID")
        ;
        return http.build();
    }
}
