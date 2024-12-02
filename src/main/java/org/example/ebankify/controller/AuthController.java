package org.example.ebankify.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ebankify.dto.auth.LoginRequest;
import org.example.ebankify.dto.auth.RegisterRequest;
import org.example.ebankify.dto.auth.LoginResponse;
import org.example.ebankify.dto.auth.VerifyUserDto;
import org.example.ebankify.entity.User;
import org.example.ebankify.mappers.UserMapper;
import org.example.ebankify.service.auth.AuthService;
import org.example.ebankify.service.jwt.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final UserMapper userMapper;

    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterRequest registerUserDto) {
        User registeredUser = authService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginRequest loginUserDto) {
        User authenticatedUser = authService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime(), userMapper.toDto(authenticatedUser));
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody @Valid VerifyUserDto verifyUserDto) {
        authService.verifyUser(verifyUserDto);
        return ResponseEntity.ok("Account verified successfully");
    }

    @PostMapping("/resend")
    public ResponseEntity<?> resendVerificationCode(@RequestParam String email) {

        authService.resendVerificationCode(email);
        return ResponseEntity.ok("Verification code sent");

    }


}