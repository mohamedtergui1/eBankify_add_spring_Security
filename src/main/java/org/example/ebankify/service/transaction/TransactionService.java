package org.example.ebankify.service.transaction;

import org.example.ebankify.entity.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction getTransaction(Long id);

    Transaction saveTransaction(Transaction transaction);

    List<Transaction> getByAuthUserTransactions(String email);


}
