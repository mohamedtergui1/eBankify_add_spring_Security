package org.example.ebankify.dto.loan.request;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanCreateDto {

    @Min(1)
    @NotNull

    private Double principal;

    @Min(1)
    @NotNull

    private Double interestRate;

    @Min(1)
    @NotNull
    private int termMonths;

}
