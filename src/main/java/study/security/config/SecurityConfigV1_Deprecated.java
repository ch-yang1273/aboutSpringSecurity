package study.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@EnableWebSecurity // @Configuration 포함되어 있음
public class SecurityConfigV1_Deprecated extends WebSecurityConfigurerAdapter {

    // Spring Security 5.4 버전에서는 보안 설정을 위해 WebSecurityConfigurerAdapter 클래스를 상속받아 사용하며,
    // configure(HttpSecurity http) 메서드를 오버라이드하여 원하는 설정을 진행하고 빈으로 등록한다.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("SecurityConfigV1_Deprecated.configure");
        http
                .authorizeRequests()
                .anyRequest().authenticated()
            .and()
                .formLogin();
    }
}
