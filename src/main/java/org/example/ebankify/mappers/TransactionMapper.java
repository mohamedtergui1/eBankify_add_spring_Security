package org.example.ebankify.mappers;

import org.example.ebankify.dto.transaction.request.TransactionCreateDto;
import org.example.ebankify.dto.transaction.response.TransactionResponseDto;
import org.example.ebankify.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mapping(source = "senderId",target = "sender.id")
    @Mapping(source = "receiverId",target = "receiver.id")
    Transaction toEntity(TransactionCreateDto transactionCreateDto);

    TransactionResponseDto toResponseDto(Transaction transaction);

}
