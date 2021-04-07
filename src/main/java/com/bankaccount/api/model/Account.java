package com.bankaccount.api.model;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@ToString
@Table(name="ACCOUNT")
public class Account {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    private Long id;

    @Column(name="CURRENT_NUMBER")
    private String accountNumber;

    @Column(name="MAX_TRANSACTION")
    private double maxTransaction;

    @Column(name="CURRENT_BALANCE")
    private double currentBalance;

    @Column(name="TYPE_ACCOUNT")
    private String typeAccount;

    @OneToMany(mappedBy="targetAccount")
    private List<UserTransaction> userTransactionList;

}
