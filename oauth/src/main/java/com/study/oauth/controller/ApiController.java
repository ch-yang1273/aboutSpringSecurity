package com.study.oauth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ApiController {

    @GetMapping("/api/user")
    public String user(Authentication authentication, @AuthenticationPrincipal OAuth2User oAuth2User) {
        log.info("authentication = {}, oAuth2User = {}", authentication, oAuth2User);
        return "user";
    }

    @GetMapping("/api/oidc")
    public String oidc(Authentication authentication, @AuthenticationPrincipal OidcUser oidcUser) {
        log.info("authentication = {}, oidcUser = {}", authentication, oidcUser);
        return "user";
    }
}
