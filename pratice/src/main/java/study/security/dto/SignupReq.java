package study.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupReq {

    private String username;
    private String password;
    private String email;
    private String role;
}
