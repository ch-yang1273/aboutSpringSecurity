package com.study.oauth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal OAuth2User oAuth2User) {

        if (oAuth2User != null) {
            String nickname = (String) oAuth2User.getAttribute("nickname");
            model.addAttribute("nickname", nickname);
        }

        return "index";
    }

    @GetMapping("/login-page")
    public String loginPage() {
        return "forward:/loginPage.html";
    }
}