package study.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorPageController {

    @GetMapping("/forbidden")
    public String forbidden() {
        return "/error/403";
    }
}
