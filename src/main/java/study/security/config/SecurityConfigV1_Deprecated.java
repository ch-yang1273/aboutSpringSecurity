package study.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//@EnableWebSecurity // @Configuration 포함되어 있음
public class SecurityConfigV1_Deprecated extends WebSecurityConfigurerAdapter {

    // Spring Security 5.4에서는 등록 된 WebSecurityConfigurerAdapter 빈을 @Override로 재작성
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
