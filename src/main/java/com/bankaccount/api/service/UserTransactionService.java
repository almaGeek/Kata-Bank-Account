package com.bankaccount.api.service;

import com.bankaccount.api.dto.TransactionDto;

import java.util.List;

public interface UserTransactionService {

    boolean addTransaction(TransactionDto transactionDto);

    List<TransactionDto> getAccountTransactions(Long id);

    List<TransactionDto> getHistoriqueTransactions();
}
