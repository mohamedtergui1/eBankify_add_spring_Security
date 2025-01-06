package org.example.ebankify.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RsendVerificationCodeRequest(
        @Email @NotBlank String email
) {
}
