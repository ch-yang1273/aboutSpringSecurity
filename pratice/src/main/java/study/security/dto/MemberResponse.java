package study.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import study.security.domain.Member;

@AllArgsConstructor
@Getter
public class MemberResponse {

    private String username;
    private String email;
    private String role;

    public static MemberResponse of(Member member) {
        return new MemberResponse(member.getUsername(), member.getEmail(), member.getRole().toString());
    }
}
