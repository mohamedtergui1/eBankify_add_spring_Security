package org.example.ebankify.repository;

import org.example.ebankify.entity.Loan;
import org.example.ebankify.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findAllByStatus(LoanStatus status);
    List<Loan> findAllByUserEmail(String user_email);
}
