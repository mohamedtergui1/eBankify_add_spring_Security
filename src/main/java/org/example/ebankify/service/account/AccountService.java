package org.example.ebankify.service.account;

import org.example.ebankify.dto.account.request.AccountCreateDto;
import org.example.ebankify.dto.account.response.AccountDtoResponse;
import org.example.ebankify.entity.Account;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    AccountDtoResponse getAccount(UUID id);

    AccountDtoResponse createAccount(AccountCreateDto account);

    AccountDtoResponse updateAccount(AccountCreateDto account , UUID id);

    void deleteAccount(UUID id);

    Page<AccountDtoResponse> getAuthUserAccounts( int page, int size);

    List<AccountDtoResponse> getAll();
}
