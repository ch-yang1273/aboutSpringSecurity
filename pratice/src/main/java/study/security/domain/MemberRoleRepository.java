package study.security.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {

    Optional<MemberRole> findByRoleName(String roleName);
}
