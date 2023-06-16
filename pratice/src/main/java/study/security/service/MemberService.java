package study.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.security.dto.MemberDto;
import study.security.domain.MemberRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void createMember(MemberDto dto) {
        memberRepository.save(dto.toEntityWithEncoder(passwordEncoder));
        log.debug("Create Member id={}, email={}", dto.getUsername(), dto.getEmail());
    }
}
