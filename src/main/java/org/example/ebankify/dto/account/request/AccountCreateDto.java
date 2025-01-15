package org.example.ebankify.dto.account.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.example.ebankify.annotation.UniqueField;
import org.example.ebankify.entity.Account;
import org.example.ebankify.enums.AccountStatus;

@Getter
public class AccountCreateDto {

    @NotNull
    private Double balance;

    @NotNull
    private AccountStatus status;

}
