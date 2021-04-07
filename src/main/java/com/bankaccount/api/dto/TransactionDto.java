package com.bankaccount.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto implements Serializable {

    private Long id;

    @NotBlank(message="Type Transaction is mandatory")
    private String typeTransaction;

    @Positive(message="Transfer amount must be positive")
    @Min(value = 10, message = "Amount must be larger than 10")
    private double amount;

    @NotBlank(message="accountNumber is mandatory")
    private String accountNumber;

    private LocalDateTime dateTransaction;
}
