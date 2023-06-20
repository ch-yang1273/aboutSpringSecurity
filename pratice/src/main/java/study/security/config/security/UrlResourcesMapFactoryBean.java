package study.security.config.security;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.util.matcher.RequestMatcher;
import study.security.service.SecurityResourceService;

import java.util.LinkedHashMap;
import java.util.List;

// 이 클래스가 왜 있어야 하는지 잘 모르겠는데...
public class UrlResourcesMapFactoryBean implements FactoryBean<LinkedHashMap<RequestMatcher, List<ConfigAttribute>>> {

    private SecurityResourceService securityResourceService;
    private LinkedHashMap<RequestMatcher, List<ConfigAttribute>> resourceMap;

    public void setSecurityResourceService(SecurityResourceService securityResourceService) {
        this.securityResourceService = securityResourceService;
    }

    // 이 메서드에서 만든 객체가 Bean이 된다.
    @Override
    public LinkedHashMap<RequestMatcher, List<ConfigAttribute>> getObject() throws Exception {

        if (resourceMap == null) {
            init();
        }

        return resourceMap;
    }

    private void init() {
        resourceMap = securityResourceService.getResourceList();
    }

    @Override
    public Class<?> getObjectType() {
        return LinkedHashMap.class;
    }

    // Singleton 인가?
    @Override
    public boolean isSingleton() {
//        return FactoryBean.super.isSingleton();
        return true;
    }
}
