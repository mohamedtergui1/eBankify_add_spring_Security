package org.example.ebankify.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.ebankify.dto.user.respense.UserDtoResponse;


@Getter
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private long expiresIn;
    private UserDtoResponse user;
}
