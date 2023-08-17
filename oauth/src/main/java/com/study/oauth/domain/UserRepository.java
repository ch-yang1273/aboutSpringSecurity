package com.study.oauth.domain;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserRepository {

    private Map<String, User> users = new HashMap<>();

    public User findByUsername(String username) {
        if (users.containsKey(username)) {
            return users.get(username);
        }
        return null;
    }

    public void save(User user) {
        if (users.containsKey(user.getUsername())) {
            return;
        }
        users.put(user.getUsername(), user);
    }
}
