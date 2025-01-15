package org.example.ebankify.dto.transaction.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.example.ebankify.enums.TransactionStatus;
import org.example.ebankify.enums.TransactionType;

import java.util.UUID;

@Getter
@Setter
public class TransactionCreateDto {

    @NotNull()
    private TransactionType type;

    @NotNull()
    @Min(value = 1, message = "Amount must be greater than 0.")
    private Double amount;

    @NotNull(message = "Receiver ID cannot be null.")
    private UUID receiverId;

    @NotNull(message = "Receiver ID cannot be null.")
    private UUID senderId;

    private Boolean sameBank;

}
