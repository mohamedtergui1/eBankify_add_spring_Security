package org.example.ebankify.service.loan;

import org.example.ebankify.dto.loan.request.LoanCreateDto;
import org.example.ebankify.dto.loan.response.LoanDto;
import org.example.ebankify.entity.Loan;

import java.util.Collection;
import java.util.List;

public interface LoanService {

    LoanDto getLoan(Long id);

    LoanDto saveLoan(LoanCreateDto loan);

    LoanDto updateLoan(LoanCreateDto loan , Long id);

    void processLoanPayments();

    List<LoanDto> getAll();
    List<LoanDto> getLoanForAuthAll();
}
