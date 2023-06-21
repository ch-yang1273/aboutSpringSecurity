package study.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.security.domain.AuthorizationResourceRepository;
import study.security.domain.MemberRepository;
import study.security.domain.MemberRole;
import study.security.domain.MemberRoleRepository;
import study.security.dto.MemberResponse;
import study.security.dto.ResourceResponse;
import study.security.dto.RoleResponse;
import study.security.dto.SignupDto;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminService {

    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final AuthorizationResourceRepository authorizationResourceRepository;

    @Transactional(readOnly = true)
    public List<MemberResponse> getMembers() {
        return memberRepository.findAll().stream()
                .map(MemberResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<RoleResponse> getRoles() {
        return memberRoleRepository.findAll().stream()
                .map(RoleResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ResourceResponse> getResource() {
        return authorizationResourceRepository.findAll().stream()
                .map(ResourceResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addRole(String roleName, String description) {
        if (roleName == null) {
            throw new IllegalArgumentException();
        }
        memberRoleRepository.save(new MemberRole(roleName, description));
    }
}
