package org.example.ebankify.service.loan;

import lombok.RequiredArgsConstructor;
import org.example.ebankify.dto.loan.request.LoanCreateDto;
import org.example.ebankify.dto.loan.response.LoanDto;
import org.example.ebankify.entity.Account;
import org.example.ebankify.entity.Loan;
import org.example.ebankify.entity.User;
import org.example.ebankify.enums.LoanStatus;
import org.example.ebankify.exception.NotFoundException;
import org.example.ebankify.mappers.LoanMapper;
import org.example.ebankify.repository.AccountRepository;
import org.example.ebankify.repository.LoanRepository;
import org.example.ebankify.repository.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final LoanMapper loanMapper;
    @Override
    public LoanDto getLoan(Long id) {
        return loanMapper.toDto(loanRepository.findById(id).orElseThrow(()-> new NotFoundException("loan not found")));
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public LoanDto saveLoan(LoanCreateDto loanCreateDto) {
        Loan loan = loanMapper.toEntity(loanCreateDto);
        loan.setStatus(LoanStatus.PENDING);
        userRepository.findById(loan.getUser().getId()).orElseThrow(()-> new NotFoundException("user not found"));
        return loanMapper.toDto(loanRepository.save(loan));
    }


    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public LoanDto updateLoan(LoanCreateDto loanCreateDto, Long id) {
        Loan loan = loanMapper.toEntity(loanCreateDto);
        if(loanRepository.existsById(id)) {
            throw new NotFoundException("loan not found");
        }
        loan.setId(id);
        return loanMapper.toDto(loanRepository.save(loan));
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
    public List<LoanDto> getAll() {
        return loanRepository.findAll().stream().map(loanMapper::toDto).toList();
    }

    @Override
    public List<LoanDto> getLoanForAuthAll() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return loanRepository.findAllByUserEmail(user.getEmail()).stream().map(loanMapper::toDto).toList();
    }


}
