package study.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfigV4_Authorization {

    // 테스트 용으로 InMemory 사용자를 생성
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        manager.createUser(User.builder().username("user").password("{noop}1111").roles("USER").build());
        manager.createUser(User.builder().username("sys").password("{noop}1111").roles("USER", "SYS").build());
        manager.createUser(User.builder().username("admin").password("{noop}1111").roles("USER", "SYS", "ADMIN").build());

        return manager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("SecurityConfigV4_Authorization.filterChain");

        http
                .authorizeRequests()
                    .antMatchers("/v4/user").hasRole("USER")
                    .antMatchers("/v4/sys/admin").hasRole("ADMIN")
                    .antMatchers("/v4/sys/**").access("hasRole('ADMIN') or hasRole('SYS')")
                .anyRequest().permitAll()
                .and()
                .formLogin();

        return http.build();
    }
}
