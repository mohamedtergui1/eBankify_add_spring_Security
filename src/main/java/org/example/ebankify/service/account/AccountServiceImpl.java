package org.example.ebankify.service.account;

import lombok.RequiredArgsConstructor;
import org.example.ebankify.dto.account.response.AccountDtoResponse;
import org.example.ebankify.entity.Account;
import org.example.ebankify.entity.User;
import org.example.ebankify.exception.BadRequest;
import org.example.ebankify.exception.DeleteUpdateException;

import org.example.ebankify.exception.NotAuthException;
import org.example.ebankify.exception.NotFoundException;

import org.example.ebankify.repository.AccountRepository;
import org.example.ebankify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;




    @Override
    public Account getAccount(long id) {

        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            return account.get();
        }
        throw new NotFoundException("Account not found");

    }

    @Override
    @Transactional
    public Account createAccount(Account account) {
        if(accountRepository.existsByAccountNumber(account.getAccountNumber())) {
            throw new BadRequest("account number already exists");
        }
        if(!userRepository.existsById(account.getUser().getId())){
            throw new BadRequest("user not  found");
        }
        return accountRepository.save(account);

    }

    @Override
    @Transactional
    public Account updateAccount(Account account) {

        if (accountRepository.findById(account.getId()).isPresent()) {
            return accountRepository.save(account);
        }
        throw new DeleteUpdateException("Account could not  found");

    }

    @Override
    @Transactional
    public void deleteAccount(long id) {

        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            accountRepository.delete(account.get());
            return;
        }
        throw new DeleteUpdateException("Account could not  found");

    }


    @Override
    public Page<Account> getAuthUserAccounts(String email, int page, int size) {

        User authUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotAuthException("You need to auth"));
        Pageable pageable = PageRequest.of(page, size);
        return accountRepository.findByUserId(authUser.getId(), pageable);

    }

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

}