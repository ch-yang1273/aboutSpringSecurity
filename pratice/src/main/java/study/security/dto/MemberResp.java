package study.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.security.domain.Member;

@AllArgsConstructor
@Getter
public class MemberResp {

    private String username;
    private String email;
    private String role;

    public static MemberResp of(Member member) {
        return new MemberResp(member.getUsername(), member.getEmail(), member.getRole().toString());
    }
}
