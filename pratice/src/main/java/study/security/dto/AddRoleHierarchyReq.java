package study.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AddRoleHierarchyReq {

    String childName;
    String parentName;
}
