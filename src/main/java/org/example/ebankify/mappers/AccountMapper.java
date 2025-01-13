package org.example.ebankify.mappers;

import org.example.ebankify.dto.account.request.AccountCreateDto;
import org.example.ebankify.dto.account.request.AccountUpdateDto;
import org.example.ebankify.dto.account.response.AccountDtoResponse;
import org.example.ebankify.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDtoResponse  toDto(Account account);

    Account toEntity(AccountCreateDto accountCreateDto);

    Account toEntity(AccountUpdateDto accountUpdateDto);
}
