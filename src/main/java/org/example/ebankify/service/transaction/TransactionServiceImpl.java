package org.example.ebankify.service.transaction;

import lombok.RequiredArgsConstructor;
import org.example.ebankify.entity.Account;
import org.example.ebankify.entity.Transaction;
import org.example.ebankify.entity.User;
import org.example.ebankify.enums.TransactionStatus;
import org.example.ebankify.exception.BadRequest;
import org.example.ebankify.exception.NotFoundException;
import org.example.ebankify.repository.AccountRepository;
import org.example.ebankify.repository.TransactionRepository;
import org.example.ebankify.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    @Override
    public Transaction getTransaction(Long id) {

        return transactionRepository.findById(id).orElseThrow(() -> new NotFoundException("Transaction not found"));

    }

    @Override
    @Transactional
    public Transaction saveTransaction(Transaction transaction) {

        Account sender = accountRepository.findById(transaction.getSender().getId()).orElseThrow(() -> new NotFoundException("Account sender not found"));
        Account receiver = accountRepository.findById(transaction.getReceiver().getId()).orElseThrow(() -> new NotFoundException("Account receiver not found"));
        if ( sender.getBalance() < transaction.getAmount()) {
            throw new BadRequest("tha balance is not enough");
        }
        transaction.setStatus(TransactionStatus.COMPLETED);
        sender.setBalance(sender.getBalance() - transaction.getAmount());
        receiver.setBalance(receiver.getBalance() + transaction.getAmount());
        accountRepository.save(sender);
        accountRepository.save(receiver);
        return transactionRepository.save(transaction);

    }

    @Override
    public List<Transaction> getByAuthUserTransactions(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));

        return transactionRepository.findByUserId(user.getId());
    }

}
