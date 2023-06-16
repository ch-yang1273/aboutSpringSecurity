package study.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
//@EnableWebSecurity
public class SecurityConfigV3_FormLogin {

    private final UserDetailsService userDetailsService;

    /**
     * 기본적으로
     * **********
     * authorizeRequests() : HTTP 서블릿 요청에 대한 보안을 구성할 때 사용한다.
     * anyRequest().authenticated() : 어떤 요청에 대해서든 인증을 받은 사용자만 접근을 허용한다.
     * **********
     * formLogin : form 로그인 방식의 인증을 한다.
     * loginPage : 로그인이 필요할 때, 이 경로로 Redirecting 된다. 로그인폼을 반환해줘야 한다.
     * defaultSuccessUrl : 로그인 성공 시, 이 경로로 Redirecting 된다.
     * failureUrl : 로그인 실패 시, 이 경로로 Redirecting 된다.
     * usernameParameter : 로그인폼 input 태그의 name
     * passwordParameter : 로그인폼 input 태그의 name
     * loginProcessingUrl : 로그인폼의 action 경로. 따로 Mapping된 Controller 메서드는 없어도 된다.
     *   - 필터에서 처리하고 매핑된 메서드는 호출되지도 않는다.
     *   - <form th:action="@{/login}" class="form-signin" method="post">
     * successHandler : 로그인 성공 시 호출되는 handler. 리다이렉팅 처리 직접 해줘야한다.
     *   - System.out.println() 만 하니 출력만하고 리다이렉팅은 되지 않았음
     *   - 특별한 경우 아니면 호출할 필요 없겠다.
     * failureHandler : 로그인 실패 시 호출되는 handler. 리다이렉팅 처리 직접 해줘야한다.
     *   - System.out.println() 만 하니 출력만하고 리다이렉팅은 되지 않았음
     *   - 특별한 경우 아니면 호출할 필요 없겠다.
     * permitAll : formLogin()에 연결되어 있다. 로그인 페이지와 관련된 모든 요청만 접근을 허용
     *   - formLogin().permitAll() 은 위의 anyRequest().authenticated() 보다 우선 순위가 높다.
     * **********
     * deleteCookies : 쿠키 삭제. 여기서는 세션 ID와 remember-me 쿠키를 삭제했다.
     * addLogoutHandler : 로그아웃을 처리하는 핸들러 설정. 안하면 기본 핸들러를 사용한다.
     * logoutSuccessHandler : 로그아웃 성공 후 실행되는 핸들러. 안하면 기본 핸들러를 사용한다.
     * **********
     * rememberMeParameter : 쿠키, 파라미터
     * tokenValiditySeconds : 쿠키 유효 시간 설정. default는 14일
     * userDetailsService : 사용자의 정보를 불러온다.
     * **********
     * sessionManagement : 세션 관리 시작 (SessionManagementFilter, ConcurrentSessionFilter)
     * sessionCreationPolicy
     *   - Always : 항상 세션을 생성
     *   - If_Required : (기본값) 필요한 경우에만 생성
     *   - Never : 세션을 생성하지 않음. But 이미 존재하는 세션은 사용
     *   - Stateless : 세션을 생성하거나, 사용하지 않음. JWT와 같은 토큰 기반 인증할 때 사용
     * maximumSessions : 유지할 최대 세션
     * maxSessionsPreventsLogin : true = 새로운 로그인 요청 차단, false = 기존 세션 만료
     * expiredUrl : 세션 만료 시 이동
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("SecurityConfigV3_FormLogin.filterChain");
        http
                // 인가 정책
                .authorizeRequests()
                .anyRequest().fullyAuthenticated()
                .and()
                // 인증 정책
                .formLogin()
                .loginPage("/login-page")
                .defaultSuccessUrl("/")
                .failureUrl("/login-page?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/login-proc")
                .permitAll() // formLogin에서 설정한 경로에 대한 permitAll()
                .and()
                // logout 정책
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/login-page")
                .deleteCookies("JSESSIONID", "remember-me")
                .and()
                // Remember-me
                .rememberMe()
                .rememberMeParameter("remember-me")
                .tokenValiditySeconds(3600)
                .userDetailsService(userDetailsService)
                .and()
                // 세션 관리
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .expiredUrl("/expired");

        return http.build();
    }
}
