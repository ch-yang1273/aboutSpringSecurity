package study.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.security.domain.MemberRole;
import study.security.domain.MemberRoleHierarchy;
import study.security.domain.MemberRoleHierarchyRepository;
import study.security.domain.MemberRoleRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MemberRoleHierarchyService {

    private final MemberRoleRepository memberRoleRepository;
    private final MemberRoleHierarchyRepository memberRoleHierarchyRepository;

    @Transactional(readOnly = true)
    public String findAllHierarchy() {

        List<MemberRoleHierarchy> roleHierarchyList = memberRoleHierarchyRepository.findAll();

        StringBuilder concat = new StringBuilder();

        for (MemberRoleHierarchy hierarchy : roleHierarchyList) {
            if (hierarchy.getParentName() != null) {
                concat.append(hierarchy.getParentName());
                concat.append(" > ");
                concat.append((hierarchy.getChildName()));
                concat.append("\n");
            }
        }

        return concat.toString();
    }

    @Transactional
    public void createMemberRoleHierarchy(String childName, String parentName) {
        MemberRole child = memberRoleRepository.findByRoleName(childName).orElseThrow();
        MemberRole parent = memberRoleRepository.findByRoleName(parentName).orElseThrow();

        // 부모의 Role 관계
        MemberRoleHierarchy hierarchy = memberRoleHierarchyRepository.findByChildName(parent.getRoleName()).orElseGet(
                () -> new MemberRoleHierarchy(parent.getRoleName(), null)
        );
        MemberRoleHierarchy parentRoleHierarchy = memberRoleHierarchyRepository.save(hierarchy);

        hierarchy = memberRoleHierarchyRepository.findByChildName(child.getRoleName()).orElseGet(
                () -> new MemberRoleHierarchy(child.getRoleName(), parent.getRoleName())
        );

        memberRoleHierarchyRepository.save(hierarchy);
    }
}
