package org.example.ebankify.dto.transaction.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.ebankify.dto.account.response.AccountDtoResponse;
import org.example.ebankify.enums.TransactionStatus;
import org.example.ebankify.enums.TransactionType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDto {

    private Long id;

    private TransactionType type;

    private Double amount;

    private TransactionStatus status;

    private AccountDtoResponse sender;

    private AccountDtoResponse receiver;

}
