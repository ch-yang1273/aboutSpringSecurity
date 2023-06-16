package study.security.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import study.security.domain.Member;

@Getter
@Setter
public class MemberDto {

    private String username;
    private String password;
    private String email;
    private String role;

    public Member toEntityWithEncoder(PasswordEncoder encoder) {
        String encodedPassword = encoder.encode(this.password);
        return Member.builder()
                .username(username)
                .password(encodedPassword)
                .email(email)
                .role(role)
                .build();
    }
}
