package org.example.ebankify.service.loan;

import org.example.ebankify.entity.Loan;

import java.util.Collection;
import java.util.List;

public interface LoanService {

    Loan getLoan(Long id);

    Loan saveLoan(Loan loan);

    Loan updateLoan(Loan loan);

    void processLoanPayments();

    List<Loan> getAll();
    List<Loan> getLoanForAuthAll(String email);
}
