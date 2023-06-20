package study.security.config.security;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import study.security.service.SecurityResourceService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private final SecurityResourceService securityResourceService;
    private LinkedHashMap<RequestMatcher, List<ConfigAttribute>> requestMap;

    public UrlFilterInvocationSecurityMetadataSource(SecurityResourceService securityResourceService) {
        this.securityResourceService = securityResourceService;
        reload();
    }

    // 파라미터인 `Object`에는 `FilterInvocation` 클래스가 들어온다.
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        HttpServletRequest request = ((FilterInvocation) object).getRequest();

        if (requestMap != null) {
            Set<Map.Entry<RequestMatcher, List<ConfigAttribute>>> entries = requestMap.entrySet();
            for (Map.Entry<RequestMatcher, List<ConfigAttribute>> entry : entries) {
                RequestMatcher matcher = entry.getKey();
                // URL 정보가 일치하는 지 확인
                if (matcher.matches(request)) {
                    return entry.getValue();
                }
            }
        }

        return null;
    }

    // `DefaultFilterInvocationSecurityMetadataSource`에서 가져왔음
    // 쓰지는 않는다.
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<ConfigAttribute> allAttributes = new HashSet<>();
        this.requestMap.values().forEach(allAttributes::addAll);
        return allAttributes;
    }

    // `DefaultFilterInvocationSecurityMetadataSource`에서 가져왔음
    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    public void reload() {
        requestMap = securityResourceService.getResourceList();
    }
}
