package org.example.ebankify.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.ebankify.enums.LoanStatus;
@Entity
@Table(name = "loans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double principal;

    @Column(nullable = false, name = "interest_rate")
    private Double interestRate;

    @Column(nullable = false , name = "term_months")
    private int termMonths;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public Double calculateMonthlyPayment() {

        double monthlyInterestRate = (interestRate / 100) / 12;


        if (monthlyInterestRate == 0) {
            return principal / termMonths;
        }

        double numerator = principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, termMonths);
        double denominator = Math.pow(1 + monthlyInterestRate, termMonths) - 1;

        return numerator / denominator;
    }
}
