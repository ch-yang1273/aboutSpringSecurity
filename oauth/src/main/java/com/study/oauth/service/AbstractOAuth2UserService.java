package com.study.oauth.service;

import com.study.oauth.domain.User;
import com.study.oauth.domain.UserRepository;
import com.study.oauth.domain.UserService;
import com.study.oauth.model.KakaoUser;
import com.study.oauth.model.ProviderUser;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Getter
@RequiredArgsConstructor
@Service
public abstract class AbstractOAuth2UserService {

    private final UserService userService;

    public void register(ProviderUser providerUser, OAuth2UserRequest userRequest) {

        User user = userService.findByUsername(providerUser.getUsername());

        if (user == null) {
            String registrationId = userRequest.getClientRegistration().getRegistrationId();
            userService.register(registrationId, providerUser);
        } else {
            System.out.println("user = " + user);
        }
    }

    public ProviderUser providerUser(ClientRegistration clientRegistration, OAuth2User oAuth2User) {

        String registrationId = clientRegistration.getRegistrationId();

        if (registrationId.equals("kakao")) {
            return new KakaoUser((OidcUser)oAuth2User, clientRegistration);
        }
        return null;
    }
}
