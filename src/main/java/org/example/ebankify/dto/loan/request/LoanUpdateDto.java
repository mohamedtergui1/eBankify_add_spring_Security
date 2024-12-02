package org.example.ebankify.dto.loan.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.ebankify.dto.user.respense.UserDtoResponse;
import org.example.ebankify.enums.LoanStatus;

@Getter
@Setter
public class LoanUpdateDto {
    @NotNull
    private Long id;

    @NotNull
    @Min(1)
    private Double principal;
    @NotNull
    @Min(1)
    private Double interestRate;
    @NotNull
    @Min(1)
    private int termMonths;
    @NotNull
    private LoanStatus status;
    @NotNull
    private Long userId;

}
