package com.bankaccount.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto implements Serializable {

    private Long id;

    @NotBlank(message="Type Transaction is mandatory")
    private String accountNumber;

    @Positive(message="Max Transaction must be positive")
    @Min(value = 10, message = "Max Transaction must be larger than 10")
    private double maxTransaction;

    @Positive(message="Current Balance must be positive")
    @Min(value = 0, message = "Max Transaction must be larger than 10")
    private double currentBalance;

    @NotBlank(message="Type Account is mandatory")
    private String typeAccount;
}
