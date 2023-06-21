package study.security.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Table(name = "MEMBER_ROLE")
@Entity
public class MemberRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "rele_desc")
    private String description;

    public MemberRole(String roleName, String description) {
        this.roleName = roleName;
        this.description = description;
    }

    public MemberRole(Long id, String roleName, String description) {
        this.id = id;
        this.roleName = roleName;
        this.description = description;
    }

    @Override
    public String toString() {
        return roleName;
    }
}
