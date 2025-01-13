package org.example.ebankify.service.account;

import lombok.RequiredArgsConstructor;
import org.example.ebankify.dto.account.request.AccountCreateDto;
import org.example.ebankify.dto.account.response.AccountDtoResponse;
import org.example.ebankify.entity.Account;
import org.example.ebankify.entity.User;
import org.example.ebankify.exception.*;

import org.example.ebankify.mappers.AccountMapper;
import org.example.ebankify.repository.AccountRepository;
import org.example.ebankify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountMapper accountMapper;
    @Override
    public AccountDtoResponse getAccount(UUID id) {
       return  accountMapper.toDto(accountRepository.findById(id).orElseThrow(()-> new NotFoundException("account not found")));
    }

    @Override
    @Transactional
    public AccountDtoResponse createAccount(AccountCreateDto account) {
        return accountMapper.toDto(accountRepository.save(accountMapper.toEntity(account)));
    }

    @Override
    @Transactional
    public AccountDtoResponse updateAccount(AccountCreateDto accountCreateDto, UUID id) {
        if (accountRepository.existsById(id)) {
            Account account = accountMapper.toEntity(accountCreateDto);
            account.setId(id);
            return accountMapper.toDto(accountRepository.save(account));
        }
        throw new DeleteUpdateException("Account could not  found");

    }

    @Override
    @Transactional
    public void deleteAccount(UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticationUser = (User) authentication.getPrincipal();

        Account account = accountRepository.findById(id).orElseThrow(() -> new DeleteUpdateException("Account could not  found"));
        if (!account.getUser().getId().equals(authenticationUser.getId())) {
            throw new PermissionException("you dont have permission");
        }


        accountRepository.delete(account);

    }

    @Override
    public Page<AccountDtoResponse> getAuthUserAccounts( int page, int size) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticationUser = (User) authentication.getPrincipal();
        User authUser = userRepository.findByEmail(authenticationUser.getEmail())
                .orElseThrow(() -> new NotAuthException("You need to auth"));
        Pageable pageable = PageRequest.of(page, size);
        return accountRepository.findByUserId(authUser.getId(), pageable).map(accountMapper::toDto);
    }

    @Override
    public List<AccountDtoResponse> getAll() {
        return accountRepository.findAll().stream().map(accountMapper::toDto).toList();
    }

}