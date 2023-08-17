package com.study.oauth.domain;

import com.study.oauth.model.ProviderUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void register(String registrationId, ProviderUser providerUser) {

        User user = User.builder()
                .registrationId(registrationId)
                .provider(providerUser.getProvider())
                .id(providerUser.getId())
                .username(providerUser.getUsername())
                .email(providerUser.getEmail())
                .authority(providerUser.getAuthorities())
                .build();

        userRepository.save(user);
    }
}
