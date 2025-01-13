package org.example.ebankify.repository;

import org.example.ebankify.entity.Account;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByUserId(Long aLong);

    Page<Account> findByUserId(Long id, Pageable pageable);
}