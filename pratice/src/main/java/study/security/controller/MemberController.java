package study.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import study.security.service.MemberService;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/mypage")
    public String myPage() {
        return "/member/myPage";
    }
}
