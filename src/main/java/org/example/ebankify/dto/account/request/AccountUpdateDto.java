package org.example.ebankify.dto.account.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.example.ebankify.enums.AccountStatus;

@Getter
public class AccountUpdateDto {
    @NotNull
    private Long id;

    @NotNull
    private Double balance;

    @NotNull
    private String accountNumber;

    @NotNull
    private AccountStatus status;

    @NotNull
    private Long userId;
}