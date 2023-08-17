package com.study.oauth.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Getter
public class User {

    private String registrationId;
    private String id;
    private String username;
    private String password;
    private String provider;
    private String email;
    private List<? extends GrantedAuthority> authority;

    @Builder
    public User(String registrationId, String id, String username, String password, String provider, String email, List<? extends GrantedAuthority> authority) {
        this.registrationId = registrationId;
        this.id = id;
        this.username = username;
        this.password = password;
        this.provider = provider;
        this.email = email;
        this.authority = authority;
    }
}
