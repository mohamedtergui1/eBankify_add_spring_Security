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

    @GetMapping("/{id}")
    public LoanDto getLoanId(@PathVariable Long id) {
        return loanService.getLoan(id);
    }

    @PostMapping
    public LoanDto createLoan(@RequestBody @Valid LoanCreateDto loanCreateDto) {
        return loanService.saveLoan(loanCreateDto);
    }

    @GetMapping
    public List<LoanDto> getLoanForAuthAll(){
        return loanService.getLoanForAuthAll();
    }

}
