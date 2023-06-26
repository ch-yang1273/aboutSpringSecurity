package study.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AddRoleReq {

    private String roleName;
    private String description;
}
