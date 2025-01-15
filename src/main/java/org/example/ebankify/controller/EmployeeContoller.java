package org.example.ebankify.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ebankify.dto.account.request.AccountCreateDto;
import org.example.ebankify.dto.account.request.AccountUpdateDto;
import org.example.ebankify.dto.account.response.AccountDtoResponse;
import org.example.ebankify.dto.loan.request.LoanCreateDto;
import org.example.ebankify.dto.loan.request.LoanUpdateDto;
import org.example.ebankify.dto.loan.response.LoanDto;

import org.example.ebankify.entity.Loan;
import org.example.ebankify.mappers.AccountMapper;
import org.example.ebankify.mappers.LoanMapper;
import org.example.ebankify.service.account.AccountService;
import org.example.ebankify.service.loan.LoanService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeContoller {

    private final LoanService loanService;
    private final LoanMapper loanMapper;
    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @PutMapping("/loan/{id}")
    public LoanDto updateLoan(@RequestBody @Valid LoanCreateDto loanCreateDto , @PathVariable Long id) {
        return  loanService.updateLoan(loanCreateDto,id);
    }

    @PutMapping("/account/{id}")
    public AccountDtoResponse updateAccount(@RequestBody @Valid AccountCreateDto accountCreateDto, @PathVariable UUID id) {
        return accountService.updateAccount(accountCreateDto,id);
    }

    @GetMapping("/account")
    public List<AccountDtoResponse> getAccounts() {
        return accountService.getAll();
    }

    @GetMapping("/loan")
    public List<LoanDto> getLoans() {
        return loanService.getAll();
    }
}
