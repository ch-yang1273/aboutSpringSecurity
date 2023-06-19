package study.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/manage")
@Controller
public class ManageController {

    @GetMapping
    public String manageHome() {
        return "manage/manage-home";
    }
}
