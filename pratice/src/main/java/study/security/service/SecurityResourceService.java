package study.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import study.security.domain.AuthorizationResource;
import study.security.domain.AuthorizationResourceRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@RequiredArgsConstructor
@Component
public class SecurityResourceService {

    private final AuthorizationResourceRepository authorizationResourceRepository;

    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getResourceList() {

        LinkedHashMap<RequestMatcher, List<ConfigAttribute>> result = new LinkedHashMap<>();

        List<AuthorizationResource> resourceList = authorizationResourceRepository.findAllUrlResources();
        resourceList.forEach(re -> {
            List<ConfigAttribute> configAttributeList = new ArrayList<>();

            // 예제와 달리 Role은 하나만 넣었다.
            String roleName = re.getMemberRole().getRoleName();
            configAttributeList.add(new SecurityConfig(roleName));
            result.put(new AntPathRequestMatcher(re.getResourceName()), configAttributeList);
        });

        return result;
    }
}
