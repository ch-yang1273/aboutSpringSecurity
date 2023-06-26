package study.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import study.security.dto.RoleResp;
import study.security.dto.SignupReq;
import study.security.service.MemberRoleService;
import study.security.service.MemberService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AuthenticationController {

    private final MemberService memberService;
    private final MemberRoleService memberRoleService;

    @GetMapping("/login")
    public String loginForm() {
        return "authentication/login";
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        List<RoleResp> roles = memberRoleService.getRoles();
        model.addAttribute("roles", roles);
        return "authentication/signup";
    }

    @PostMapping("/signup-proc")
    public String createMember(@ModelAttribute SignupReq dto) {
        memberService.createMember(dto);
        return "redirect:/login";
    }
}
