package study.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/v5")
@Controller
public class SecurityControllerV5 {

    @GetMapping
    public String csrfForm() {
        return "csrf";
    }

    @PostMapping("/csrf")
    public String csrfPost(@RequestParam String text) {
        System.out.println(text);
        return "redirect:/";
    }
}
