package com.study.oauth.model;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

public class KakaoUser extends OidcProviderUser {

    public KakaoUser(OidcUser oidcUser, ClientRegistration clientRegistration) {
        super(oidcUser.getAttributes(), oidcUser, clientRegistration);
    }

    @Override
    public String getId() {
        return (String)getAttributes().get("sub");
    }

    @Override
    public String getUsername() {
        return (String)getAttributes().get("nickname");
    }
}
