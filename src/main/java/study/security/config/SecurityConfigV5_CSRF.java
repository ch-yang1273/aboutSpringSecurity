package study.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

//@EnableWebSecurity
public class SecurityConfigV5_CSRF {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("SecurityConfigV5_CSRF.securityFilterChain");

        http
                .authorizeRequests()
                .antMatchers("/v5/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin();

        return http.build();
    }
}
