package study.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.security.domain.AuthorizationResource;
import study.security.domain.MemberRole;

@AllArgsConstructor
@Getter
public class ResourceResponse {

    private String resourceName;
    private String httpMethod;
    private int orderNum;
    private String resourceType;
    private String memberRole;

    public static ResourceResponse of(AuthorizationResource resource) {
        return new ResourceResponse(resource.getResourceName(), resource.getHttpMethod(), resource.getOrderNum(),
                resource.getResourceType(), resource.getMemberRole().toString());
    }
}
