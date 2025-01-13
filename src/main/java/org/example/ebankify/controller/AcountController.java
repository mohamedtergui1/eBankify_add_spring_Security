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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users/account")
public class AcountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    private final UserService userService;

    @GetMapping
    public Page<AccountDtoResponse> authUserAccounts(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticationUser = (User) authentication.getPrincipal();

        return accountService.getAuthUserAccounts( authenticationUser.getEmail() , page, size).map(accountMapper::toDto);
    }

    @PostMapping
    public AccountDtoResponse createAccount(@RequestBody AccountCreateDto accountCreateDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticationUser = (User) authentication.getPrincipal();

        Account account = accountMapper.toEntity(accountCreateDto);
        account.setUser(authenticationUser);
        return accountMapper.toDto(accountService.createAccount(account));
    }

    @PutMapping
    public AccountDtoResponse updateAccount(@RequestBody AccountUpdateDto accountUpdateDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User authenticationUser = (User) authentication.getPrincipal();

        Account account = accountService.getAccount(accountUpdateDto.getId());
        if (account.getUser().getId().equals(authenticationUser.getId())) {
            throw new PermissionException("you dont have permission");
        }

        return accountMapper.toDto(accountService.updateAccount(accountMapper.toEntity(accountUpdateDto)));
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {

        accountService.deleteAccount(id);
    }

}