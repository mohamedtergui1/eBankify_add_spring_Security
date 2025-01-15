package org.example.ebankify.dto.account.response;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.ebankify.dto.user.respense.UserDtoResponse;
import org.example.ebankify.enums.AccountStatus;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDtoResponse {

    private UUID id;

    private Double balance;

    private AccountStatus status;

    private UserDtoResponse user;

}