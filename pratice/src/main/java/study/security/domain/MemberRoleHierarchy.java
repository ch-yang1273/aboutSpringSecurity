package study.security.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class MemberRoleHierarchy {

    @Id
    @GeneratedValue
    @Column(name = "hierarchy_id")
    private Long id;

    @Column(name = "child_name")
    private String childName;

    @Column(name = "parent_name")
    private String parentName;

    @Builder
    public MemberRoleHierarchy(String childName, String parentName) {
        this.childName = childName;
        this.parentName = parentName;
    }
}
