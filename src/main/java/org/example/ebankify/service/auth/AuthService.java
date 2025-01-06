package org.example.ebankify.service.auth;

import org.example.ebankify.dto.auth.LoginRequest;
import org.example.ebankify.dto.auth.RegisterRequest;
import org.example.ebankify.dto.auth.RsendVerificationCodeRequest;
import org.example.ebankify.dto.auth.VerifyUserDto;
import org.example.ebankify.dto.user.respense.UserDtoResponse;
import org.example.ebankify.entity.User;

public interface AuthService {
    User signup(RegisterRequest input);
    User authenticate(LoginRequest input);
    void verifyUser(VerifyUserDto input);
    void resendVerificationCode(RsendVerificationCodeRequest rsendVerificationCodeRequest);
    UserDtoResponse authUser();
}
