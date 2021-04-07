package com.bankaccount.api.model;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@ToString
@Table(name="USER_TRANSACTION")
public class UserTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name="TYPE_TRANSACTION")
    private String typeTransaction;

    private double amount;

    @Column(name="DATE_TRANSACTION")
    private LocalDateTime dateTransaction;

    @ManyToOne
    @JoinColumn(name="ACCOUNT_ID")
    private Account targetAccount;

}
