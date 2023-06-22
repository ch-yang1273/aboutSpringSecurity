package study.security.config.security.authorization;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import study.security.config.security.authentication.AuthenticationBeans;
import study.security.service.SecurityResourceService;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class AuthorizationBeans {

    private final SecurityResourceService securityResourceService;
    private final AuthenticationBeans authenticationBeans;

    private List<AccessDecisionVoter<?>> getAccessDecisionVoters() {
        return List.of(new RoleVoter());
    }

    private AccessDecisionManager affirmativeBased() {
        return new AffirmativeBased(getAccessDecisionVoters());
    }

    // 한번만 사용될 것을 예상해서 @Bean 등록하지 않았다.
    public AjaxAccessDeniedHandler accessDeniedHandler() {
        return new AjaxAccessDeniedHandler();
    }

    @Bean
    public UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource() throws Exception {
        return new UrlFilterInvocationSecurityMetadataSource(securityResourceService);
    }

    @Bean
    public FilterSecurityInterceptor filterSecurityInterceptor(HttpSecurity http) throws Exception {

        FilterSecurityInterceptor fsi = new FilterSecurityInterceptor();
        fsi.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource());
        fsi.setAccessDecisionManager(affirmativeBased());

        /**
         * 이미 인증 필터를 거친 후 도달하게 되는, 인가 필터 "FilterSecurityInterceptor"에서
         * 추가적인 인증 작업이 필요없는데, 인증 필터에서 사용한 동일한 "AuthenticationManager"를 사용하는 것이 의미가 있나 싶다.
         * 디버거 열어서 사용은 되는지 확인해보고 의미 없으면 지우도록 합시다.
         */
        fsi.setAuthenticationManager(authenticationBeans.ajaxAuthenticationManager(http));

        return fsi;
    }
}
