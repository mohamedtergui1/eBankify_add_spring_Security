package org.example.ebankify.dto.user.request;


import lombok.Getter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Getter
public class LoginRequest {
    @NotBlank(message = "email is require")
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "password is require")
    private String password;
}