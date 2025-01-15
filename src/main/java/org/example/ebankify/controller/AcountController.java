package org.example.ebankify.controller;

import lombok.RequiredArgsConstructor;
import org.example.ebankify.dto.account.request.AccountCreateDto;
import org.example.ebankify.dto.account.response.AccountDtoResponse;
import org.example.ebankify.mappers.AccountMapper;
import org.example.ebankify.service.account.AccountService;
import org.example.ebankify.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/account")
public class AcountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    private final UserService userService;

    @GetMapping
    public Page<AccountDtoResponse> authUserAccounts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        return accountService.getAuthUserAccounts(page, size);
    }

    @PostMapping
    public AccountDtoResponse createAccount(@RequestBody AccountCreateDto accountCreateDto) {
        return accountService.createAccount(accountCreateDto);
    }

    @PutMapping("/{id}")
    public AccountDtoResponse updateAccount(@RequestBody AccountCreateDto accountCreateDto, @PathVariable UUID id) {
        return accountService.updateAccount(accountCreateDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable UUID id) {
        accountService.deleteAccount(id);
    }

}