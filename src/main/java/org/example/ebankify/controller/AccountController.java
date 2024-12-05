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
import org.example.ebankify.service.jwt.JwtService;
import org.example.ebankify.service.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final UserService userService;
    private final JwtService jwtService;


    private String getAuthenticatedUserEmail() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }

    @GetMapping
    public Page<AccountDtoResponse> authUserAccounts(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        // Use the helper method to get the authenticated user's email
        String email = getAuthenticatedUserEmail();
        return accountService.getAuthUserAccounts(email, page, size).map(accountMapper::toDto);
    }

    @PostMapping
    public AccountDtoResponse createAccount(@RequestBody AccountCreateDto accountCreateDto) {

        String email = getAuthenticatedUserEmail();
        User user = userService.getUserByEmail(email);
        Account account = accountMapper.toEntity(accountCreateDto);
        account.setUser(user);
        return accountMapper.toDto(accountService.createAccount(account));
    }

    @PutMapping
    public AccountDtoResponse updateAccount(@RequestBody AccountUpdateDto accountUpdateDto) {
        // Use the helper method to get the authenticated user's email
        String email = getAuthenticatedUserEmail();
        User user = userService.getUserByEmail(email);
        Account account = accountService.getAccount(accountUpdateDto.getId());
        if (!account.getUser().getId().equals(user.getId())) {
            throw new PermissionException("You don't have permission to modify this account");
        }
        return accountMapper.toDto(accountService.updateAccount(accountMapper.toEntity(accountUpdateDto)));
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        // Use the helper method to get the authenticated user's email
        String email = getAuthenticatedUserEmail();
        User user = userService.getUserByEmail(email);
        Account account = accountService.getAccount(id);
        if (!account.getUser().getId().equals(user.getId())) {
            throw new PermissionException("You don't have permission to delete this account");
        }
        accountService.deleteAccount(id);
    }
}
