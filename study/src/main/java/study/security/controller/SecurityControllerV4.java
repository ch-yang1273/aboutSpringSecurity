package study.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v4")
@Controller
public class SecurityControllerV4 {

    @GetMapping("/user")
    public String user() {
        return "home";
    }

    @GetMapping("/sys/admin")
    public String admin() {
        return "home";
    }

    @GetMapping("/sys")
    public String sys() {
        return "home";
    }
}
