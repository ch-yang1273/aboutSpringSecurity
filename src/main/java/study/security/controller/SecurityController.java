package study.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SecurityController {

    @GetMapping("/")
    public String index() {
        System.out.println("SecurityController.index");
        return "home";
    }

    @GetMapping("/login-page")
    public String login() {
        System.out.println("SecurityController.login");
        return "login";
    }

    @GetMapping("/denied")
    public @ResponseBody String denied() {
        return "denied";
    }
}
