package study.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * The WebSecurityConfigurerAdapter is deprecated
 * 공식 블로그: https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
 * 컴포넌트 기반의 보안 설정으로 전환을 권장
 * WebSecurityConfigurerAdapter을 상속해서 생성하는 것이 보안 설정의 유연성을 제한하는 것이 문제였던 것 같다.
 * Spring Security가 제공하는 기본 Login form과 Logout form을 사용한다.
 * Login URL : "/login"
 * Logout URL : "/logout"
 */
//@Configuration
public class SecurityConfigV2 {

    // Spring Security 5.7 부터는 SecurityFilterChain Bean을 등록하는 방식을 권장
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("SecurityConfigV2.filterChain");
        http
                .authorizeRequests()
                .anyRequest().authenticated()
            .and()
                .formLogin();

        return http.build();
    }
}
