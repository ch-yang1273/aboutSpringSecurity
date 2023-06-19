package study.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import study.security.dto.MemberDto;
import study.security.service.MemberService;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthenticationController {

    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm() {
        return "authentication/login";
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "authentication/signup";
    }

    @PostMapping("/signup-proc")
    public String createMember(@ModelAttribute MemberDto dto) {
        memberService.createMember(dto);
        return "redirect:/login";
    }
}
