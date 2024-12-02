package org.example.ebankify.repository;

import org.example.ebankify.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query(value = "select transaction.* from  transaction join  accounts on accounts.id = transaction.receiver_id or  accounts.id = transaction.receiver_id   ",nativeQuery = true)
    List<Transaction> findByUserId(Long userId);

}