package study.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.security.domain.AuthorizationResource;

@AllArgsConstructor
@Getter
public class ResourceResp {

    private String resourceName;
    private String httpMethod;
    private int orderNum;
    private String resourceType;
    private String memberRole;

    public static ResourceResp of(AuthorizationResource resource) {
        return new ResourceResp(resource.getResourceName(), resource.getHttpMethod(), resource.getOrderNum(),
                resource.getResourceType(), resource.getMemberRole().toString());
    }
}
