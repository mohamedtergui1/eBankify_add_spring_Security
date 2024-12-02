package org.example.ebankify.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ebankify.dto.loan.request.LoanCreateDto;
import org.example.ebankify.dto.loan.response.LoanDto;
import org.example.ebankify.entity.Loan;
import org.example.ebankify.mappers.LoanMapper;
import org.example.ebankify.service.loan.LoanService;
import org.example.ebankify.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("users/loan")
public class LoanController {

    private final LoanService loanService;
    private final LoanMapper loanMapper;
    private final UserService userService;
    private final Jwt jwt;

    @GetMapping("/{id}")
    public LoanDto getLoanId(@PathVariable Long id) {
        return loanMapper.toDto(loanService.getLoan(id));
    }

    @PostMapping
    public LoanDto createLoan(@RequestBody @Valid LoanCreateDto loanCreateDto, @RequestHeader("Authorization") String token) {
        Loan  loan = loanMapper.toEntity(loanCreateDto);
        loan.setUser(userService.getUserByEmail(jwt.extractEmailString(token.substring(7))));
        return loanMapper.toDto(loanService.saveLoan(loan));
    }

    @GetMapping
    public List<LoanDto> getLoanForAuthAll(@RequestHeader("Authorization") String token){
        String email = jwt.extractEmailString(token.substring(7));
        return loanService.getLoanForAuthAll(email).stream().map(loanMapper::toDto).toList();
    }


}
