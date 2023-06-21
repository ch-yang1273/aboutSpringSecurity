package study.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import study.security.service.MemberService;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/mypage")
    public String myPage() {
        return "/member/myPage";
    }

    @GetMapping("/manage")
    public String manageHome() {
        return "manage/manage-home";
    }
}
