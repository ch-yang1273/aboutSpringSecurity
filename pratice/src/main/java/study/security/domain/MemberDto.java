package study.security.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
public class MemberDto {

    private String username;
    private String password;
    private String email;
    private String age;
    private String role;

    public Member toEntityWithEncoder(PasswordEncoder encoder) {
        String encodedPassword = encoder.encode(this.password);
        return Member.builder()
                .username(username)
                .password(encodedPassword)
                .email(email)
                .age(age)
                .role(role)
                .build();
    }
}
