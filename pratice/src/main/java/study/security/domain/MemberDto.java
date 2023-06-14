package study.security.domain;

import lombok.Getter;

@Getter
public class MemberDto {

    private String username;
    private String password;
    private String email;
    private String age;
    private String role;

    public Member toEntity() {
        return Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .age(age)
                .role(role)
                .build();
    }
}
