package study.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.security.config.security.authorization.UrlFilterInvocationSecurityMetadataSource;
import study.security.domain.AuthorizationResource;
import study.security.domain.AuthorizationResourceRepository;
import study.security.domain.MemberRole;
import study.security.domain.MemberRoleRepository;
import study.security.dto.AddResourceReq;
import study.security.dto.ResourceResp;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthorizationResourceService {

    private final AuthorizationResourceRepository authorizationResourceRepository;
    private final MemberRoleRepository memberRoleRepository;

    private final UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;

    @Transactional(readOnly = true)
    public List<ResourceResp> getResource() {
        return authorizationResourceRepository.findAll().stream()
                .map(ResourceResp::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addResource(AddResourceReq dto) {
        MemberRole memberRole = memberRoleRepository.findByRoleName(dto.getMemberRole()).orElseThrow();

        AuthorizationResource entity = AuthorizationResource.builder()
                .resourceName(dto.getResourceName())
                .httpMethod(dto.getHttpMethod())
                .orderNum(dto.getOrderNum())
                .resourceType(dto.getResourceType())
                .memberRole(memberRole)
                .build();

        authorizationResourceRepository.save(entity);

        urlFilterInvocationSecurityMetadataSource.reload();
    }
}
