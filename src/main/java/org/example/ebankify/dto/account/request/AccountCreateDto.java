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
    @Size(max = 24 )
    @UniqueField(entity = Account.class , message = "account number must be unique", field = "accountNumber")
    private String accountNumber;

    @NotNull
    private AccountStatus status;

}
