package study.security.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AddResourceReq {
    private int orderNum;
    private String resourceType;
    private String resourceName;
    private String httpMethod;
    private String memberRole;
}
