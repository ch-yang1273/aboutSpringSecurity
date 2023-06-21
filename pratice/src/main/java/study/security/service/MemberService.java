package study.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.security.domain.Member;
import study.security.domain.MemberRole;
import study.security.domain.MemberRoleRepository;
import study.security.dto.MemberResponse;
import study.security.dto.SignupDto;
import study.security.domain.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createMember(SignupDto dto) {
        MemberRole memberRole = memberRoleRepository.findByRoleName(dto.getRole()).orElseThrow();

        Member member = Member.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .role(memberRole)
                .build();

        memberRepository.save(member);
        log.debug("Create Member id={}, email={}", dto.getUsername(), dto.getEmail());
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> getMembers() {
        return memberRepository.findAll().stream()
                .map(MemberResponse::of)
                .collect(Collectors.toList());
    }
}
