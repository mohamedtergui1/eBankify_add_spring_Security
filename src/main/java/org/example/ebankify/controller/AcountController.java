package org.example.ebankify.controller;


import lombok.RequiredArgsConstructor;
import org.example.ebankify.dto.account.request.AccountCreateDto;
import org.example.ebankify.dto.account.request.AccountUpdateDto;
import org.example.ebankify.dto.account.response.AccountDtoResponse;
import org.example.ebankify.entity.Account;
import org.example.ebankify.entity.User;
import org.example.ebankify.exception.PermissionException;
import org.example.ebankify.mappers.AccountMapper;
import org.example.ebankify.service.account.AccountService;
import org.example.ebankify.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/account")
public class AcountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    private final UserService userService;

    @GetMapping
    public Page<AccountDtoResponse> authUserAccounts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestHeader("Authorization") String token) {

        String  email = "";
        return accountService.getAuthUserAccounts(email, page, size).map(accountMapper::toDto);
    }

    @PostMapping
    public AccountDtoResponse createAccount(@RequestBody AccountCreateDto accountCreateDto, @RequestHeader("Authorization") String token) {
        Account account = accountMapper.toEntity(accountCreateDto);
        account.setUser(userService.getUserByEmail(""));
        return accountMapper.toDto(accountService.createAccount(account));
    }

    @PutMapping
    public AccountDtoResponse updateAccount(@RequestBody AccountUpdateDto accountUpdateDto, @RequestHeader("Authorization") String token) {
        User user = userService.getUserByEmail("");
        Account account = accountService.getAccount(accountUpdateDto.getId());
        if (account.getUser().getId().equals(user.getId())) {
            throw new PermissionException("you dont have permission");
        }

        return accountMapper.toDto(accountService.updateAccount(accountMapper.toEntity(accountUpdateDto)));
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        User user = userService.getUserByEmail("");
        Account account = accountService.getAccount(id);
        if (account.getUser().getId().equals(user.getId())) {
            throw new PermissionException("you dont have permission");
        }
        accountService.deleteAccount(id);
    }

}