package org.example.ebankify.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyUserDto {
    @Email
    private String email;
    @NotBlank
    private String verificationCode;
}
