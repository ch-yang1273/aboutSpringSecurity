package com.study.oauth.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model model, Authentication authentication,
                        @AuthenticationPrincipal OAuth2User oAuth2User) {

        return "forward:/index.html";
    }

    @GetMapping("/login-page")
    public String loginPage() {
        return "forward:/loginPage.html";
    }
}