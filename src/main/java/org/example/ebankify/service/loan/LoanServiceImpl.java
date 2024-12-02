package org.example.ebankify.service.loan;

import lombok.RequiredArgsConstructor;
import org.example.ebankify.entity.Account;
import org.example.ebankify.entity.Loan;
import org.example.ebankify.enums.LoanStatus;
import org.example.ebankify.exception.NotAuthException;
import org.example.ebankify.exception.NotFoundException;
import org.example.ebankify.repository.AccountRepository;
import org.example.ebankify.repository.LoanRepository;
import org.example.ebankify.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Override
    public Loan getLoan(Long id) {
        return loanRepository.findById(id).orElseThrow(()-> new NotFoundException("loan not found"));
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Loan saveLoan(Loan loan) {
        loan.setStatus(LoanStatus.PENDING);
        userRepository.findById(loan.getUser().getId()).orElseThrow(()-> new NotFoundException("user not found"));
        return loanRepository.save(loan);
    }


    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Loan updateLoan(Loan loan) {
        loanRepository.findById(loan.getId()).orElseThrow(()-> new NotFoundException("loan not found"));
        userRepository.findById(loan.getUser().getId()).orElseThrow(()-> new NotFoundException("user not found"));
        return loanRepository.save(loan);
    }

    @Override
    @Scheduled(cron = "0 0 0 1 * ?")
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void processLoanPayments() {
            List<Loan> loans = loanRepository.findAllByStatus(LoanStatus.APPROVED);
            for (Loan loan : loans) {
                Optional<Account> isOptaccount = accountRepository.findByUserId(loan.getUser().getId());
                if(isOptaccount.isPresent()) {
                    Account account = isOptaccount.get();
                    account.setBalance(account.getBalance() - loan.calculateMonthlyPayment());
                    accountRepository.save(account);
                }
            }
    }

    @Override
    public List<Loan> getAll() {
        return  loanRepository.findAll();
    }

    @Override
    public List<Loan> getLoanForAuthAll(String email) {
        return loanRepository.findAllByUserEmail(email);
    }


}
