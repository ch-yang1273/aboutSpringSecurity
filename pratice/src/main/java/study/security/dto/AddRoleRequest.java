package study.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AddRoleRequest {

    private String roleName;
    private String description;
}
