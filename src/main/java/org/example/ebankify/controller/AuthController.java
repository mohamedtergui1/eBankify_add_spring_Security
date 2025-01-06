package org.example.ebankify.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ebankify.dto.ApiResponse;
import org.example.ebankify.dto.auth.*;
import org.example.ebankify.dto.user.respense.UserDtoResponse;
import org.example.ebankify.entity.User;
import org.example.ebankify.mappers.UserMapper;
import org.example.ebankify.service.auth.AuthService;
import org.example.ebankify.service.jwt.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final UserMapper userMapper;

    private final JwtService jwtService;

    @PostMapping("/auth/signup")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody @Valid RegisterRequest registerUserDto) {
        User registeredUser = authService.signup(registerUserDto);
        return ResponseEntity.ok(new ApiResponse<>("User registered successfully", registeredUser));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse<LoginResponse>> authenticate(@RequestBody @Valid LoginRequest loginUserDto) {
        User authenticatedUser = authService.authenticate(loginUserDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse(jwtToken, jwtService.getExpirationTime(), userMapper.toDto(authenticatedUser));
        return ResponseEntity.ok(new ApiResponse<>("User authenticated successfully", loginResponse));
    }

    @PostMapping("/auth/verify")
    public ResponseEntity<ApiResponse<String>> verifyUser(@RequestBody @Valid VerifyUserDto verifyUserDto) {
        authService.verifyUser(verifyUserDto);
        return ResponseEntity.ok(new ApiResponse<>("Account verified successfully", null));
    }

    @PostMapping("/auth/resend")
    public ResponseEntity<ApiResponse<String>> resendVerificationCode(@RequestBody RsendVerificationCodeRequest resendVerificationCodeRequest) {
        authService.resendVerificationCode(resendVerificationCodeRequest);
        return ResponseEntity.ok(new ApiResponse<>("Verification code sent", null));
    }

    @GetMapping("/authUser")
    public ResponseEntity<ApiResponse<UserDtoResponse>> getUser() {
        UserDtoResponse userDtoResponse = authService.authUser();
        return ResponseEntity.ok(new ApiResponse<>("User fetched successfully", userDtoResponse));
    }

}