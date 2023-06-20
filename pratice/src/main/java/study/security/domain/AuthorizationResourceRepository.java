package study.security.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorizationResourceRepository extends JpaRepository<AuthorizationResource, Long> {

    @Query("select r from AuthorizationResource r join fetch r.memberRole where r.resourceType = 'url' order by r.resourceName desc")
    List<AuthorizationResource> findAllUrlResources();
}
