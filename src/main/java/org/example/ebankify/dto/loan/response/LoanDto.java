package org.example.ebankify.dto.loan.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.ebankify.dto.user.respense.UserDtoResponse;
import org.example.ebankify.enums.LoanStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanDto {

    private Long id;

    private Double principal;

    private Double interestRate;

    private int termMonths;

    private LoanStatus status;

    private UserDtoResponse user;
}
