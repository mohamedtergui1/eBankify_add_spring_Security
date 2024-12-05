package org.example.ebankify.dto.auth;


import jakarta.validation.constraints.Size;
import lombok.Getter;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class LoginRequest {
    @NotBlank(message = "email is require")
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "password is require")
    @Size(min = 8, max = 255)
    private String password;
    private String getAuthenticatedUserEmail() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }
}