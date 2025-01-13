package org.example.ebankify.service.transaction;

import org.example.ebankify.dto.transaction.request.TransactionCreateDto;
import org.example.ebankify.dto.transaction.response.TransactionResponseDto;
import org.example.ebankify.entity.Transaction;

import java.util.List;

public interface TransactionService {

    TransactionResponseDto getTransaction(Long id);

    TransactionResponseDto saveTransaction(TransactionCreateDto transaction);

    List<TransactionResponseDto> getByAuthUserTransactions( );

}
