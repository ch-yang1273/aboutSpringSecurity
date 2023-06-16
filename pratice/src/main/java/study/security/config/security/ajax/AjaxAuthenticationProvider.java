package study.security.config.security.ajax;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import study.security.domain.MemberContext;

@Slf4j
@RequiredArgsConstructor
@Component
public class AjaxAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("AjaxAuthenticationProvider.authenticate");
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        MemberContext memberContext = (MemberContext) userDetailsService.loadUserByUsername(username);

        if (!passwordEncoder.matches(password, memberContext.getMember().getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new AjaxAuthenticationToken(
                memberContext.getMember(), null, memberContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(AjaxAuthenticationToken.class);
    }
}
