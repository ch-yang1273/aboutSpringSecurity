package study.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.security.domain.MemberRole;

@AllArgsConstructor
@Getter
public class RoleResponse {

    private String roleName;
    private String description;

    public static RoleResponse of(MemberRole role) {
        return new RoleResponse(role.getRoleName(), role.getDescription());
    }
}
