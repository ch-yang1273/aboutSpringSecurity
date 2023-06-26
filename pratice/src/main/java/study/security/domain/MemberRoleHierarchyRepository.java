package study.security.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRoleHierarchyRepository extends JpaRepository<MemberRoleHierarchy, Long> {

    Optional<MemberRoleHierarchy> findByChildName(String childName);
}
