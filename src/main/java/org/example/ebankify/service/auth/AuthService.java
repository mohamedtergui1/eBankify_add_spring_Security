package org.example.ebankify.service.auth;

import org.example.ebankify.dto.auth.LoginRequest;
import org.example.ebankify.dto.auth.RegisterRequest;
import org.example.ebankify.dto.auth.VerifyUserDto;
import org.example.ebankify.entity.User;

public interface AuthService {
    User signup(RegisterRequest input);
    public User authenticate(LoginRequest input);
    void verifyUser(VerifyUserDto input);
    void resendVerificationCode(String email);
}
