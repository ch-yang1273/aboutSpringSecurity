package study.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.security.domain.MemberRole;
import study.security.domain.MemberRoleRepository;
import study.security.dto.RoleResponse;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberRoleService {

    private final MemberRoleRepository memberRoleRepository;

    @Transactional(readOnly = true)
    public List<RoleResponse> getRoles() {
        return memberRoleRepository.findAll().stream()
                .map(RoleResponse::of)
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
