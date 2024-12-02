package org.example.ebankify.service.auth;

import org.example.ebankify.entity.User;

public interface AuthService {
    User login(User user);
    User register(User user);
}
