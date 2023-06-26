package study.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.security.domain.MemberRole;

@AllArgsConstructor
@Getter
public class RoleResp {

    private String roleName;
    private String description;

    public static RoleResp of(MemberRole role) {
        return new RoleResp(role.getRoleName(), role.getDescription());
    }
}
