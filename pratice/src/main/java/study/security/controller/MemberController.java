package study.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import study.security.domain.MemberDto;
import study.security.service.MemberService;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "member/login";
    }

    @GetMapping("signup-form")
    public String signupForm() {
        return "member/signup";
    }

    @PostMapping("/signup")
    public String createMember(MemberDto dto) {
        memberService.createMember(dto);
        return "redirect:/";
    }
}
