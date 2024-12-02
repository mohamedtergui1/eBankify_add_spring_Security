package org.example.ebankify.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.ebankify.dto.user.respense.UserDtoResponse;

@AllArgsConstructor
@Getter
public class LoginResponse {
    private String token;
    private long expiresIn;
    private UserDtoResponse user;
}
