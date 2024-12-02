package org.example.ebankify.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ebankify.dto.transaction.request.TransactionCreateDto;
import org.example.ebankify.dto.transaction.response.TransactionResponseDto;
import org.example.ebankify.mappers.TransactionMapper;
import org.example.ebankify.service.transaction.TransactionService;
import org.example.ebankify.service.user.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final UserService userService;
    private final Jwt jwt;
    private final TransactionMapper transactionMapper;

    @GetMapping
    public List<TransactionResponseDto> getAuthUserTransactions( @RequestHeader("Authorization") String token) {
        String email = jwt.extractEmailString(token.substring(7));
        return  transactionService.getByAuthUserTransactions(email).stream().map(transactionMapper::toResponseDto).toList();
    }

    @PostMapping
    public TransactionResponseDto addTransaction(@RequestBody @Valid TransactionCreateDto transactionCreateDto, @RequestHeader("Authorization") String token){

        return transactionMapper.toResponseDto(transactionService.saveTransaction(transactionMapper.toEntity(transactionCreateDto))) ;
    }
}
