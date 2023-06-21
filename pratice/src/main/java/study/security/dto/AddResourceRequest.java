package study.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import study.security.domain.AuthorizationResource;

@NoArgsConstructor
@Getter
public class AddResourceRequest {
    private int orderNum;
    private String resourceType;
    private String resourceName;
    private String httpMethod;
    private String memberRole;
}
