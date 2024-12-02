package org.example.ebankify.dto.user.request;


import jakarta.validation.constraints.*;
import lombok.Getter;
import org.example.ebankify.annotation.UniqueField;
import org.example.ebankify.entity.User;
import org.example.ebankify.enums.UserRole;


@Getter
public class UpdateUserRequest  {

    @NotNull(message = "you need to send the id for validate")
    private long id;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 30)
    private String password;

    @NotBlank(message = "name is required")
    @Size(min = 8, max = 30)
    private String name;

    @UniqueField(entity = User.class, field = "email", message = "Email must be unique")
    @NotBlank(message = "Email is required")
    private String email;

    @Max(value = 80)
    @Min(value = 18)
    @NotNull
    private int age;

    @Min(value = 0)
    @NotNull
    private Double monthlyIncome;

    @Min(value = 0)
    @NotNull
    private int creditScore;

    @NotNull
    private UserRole role;

}