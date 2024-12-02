package org.example.ebankify.mappers;

import org.example.ebankify.dto.loan.request.LoanCreateDto;
import org.example.ebankify.dto.loan.request.LoanUpdateDto;
import org.example.ebankify.dto.loan.response.LoanDto;
import org.example.ebankify.entity.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface LoanMapper {

    Loan toEntity(LoanCreateDto loanCreateDto);

    @Mapping(source = "userId", target = "user.id")
    Loan toEntity(LoanUpdateDto loanUpdateDto);

    LoanDto toDto(Loan loan);

}
