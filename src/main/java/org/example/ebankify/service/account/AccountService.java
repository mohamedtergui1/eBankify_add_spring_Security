package org.example.ebankify.service.account;

import org.example.ebankify.dto.account.response.AccountDtoResponse;
import org.example.ebankify.entity.Account;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {
    Account getAccount(long id);

    Account createAccount(Account account);

    Account updateAccount(Account account);

    void deleteAccount(long id);

    Page<Account> getAuthUserAccounts(String email, int page, int size);

    List<Account> getAll();
}
