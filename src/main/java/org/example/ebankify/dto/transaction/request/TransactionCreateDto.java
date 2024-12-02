package org.example.ebankify.dto.transaction.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.ebankify.enums.TransactionStatus;
import org.example.ebankify.enums.TransactionType;

@Getter
@Setter
public class TransactionCreateDto {

    @NotNull(message = "Transaction type cannot be null.")
    private TransactionType type;

    @NotNull(message = "Amount cannot be null.")
    @Min(value = 1, message = "Amount must be greater than 0.")
    private Double amount;

    @NotNull(message = "Receiver ID cannot be null.")
    private Long receiverId;

    @NotNull(message = "Receiver ID cannot be null.")
    private Long senderId;

    private Boolean sameBank;
}
