package study.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.security.domain.AuthorizationResource;
import study.security.domain.AuthorizationResourceRepository;
import study.security.domain.MemberRole;
import study.security.domain.MemberRoleRepository;
import study.security.dto.AddResourceRequest;
import study.security.dto.ResourceResponse;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthorizationResourceService {

    private final AuthorizationResourceRepository authorizationResourceRepository;
    private final MemberRoleRepository memberRoleRepository;

    @Transactional(readOnly = true)
    public List<ResourceResponse> getResource() {
        return authorizationResourceRepository.findAll().stream()
                .map(ResourceResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addResource(AddResourceRequest dto) {
        MemberRole memberRole = memberRoleRepository.findByRoleName(dto.getMemberRole()).orElseThrow();

        AuthorizationResource entity = AuthorizationResource.builder()
                .resourceName(dto.getResourceName())
                .httpMethod(dto.getHttpMethod())
                .orderNum(dto.getOrderNum())
                .resourceType(dto.getResourceType())
                .memberRole(memberRole)
                .build();

        authorizationResourceRepository.save(entity);
    }
}
