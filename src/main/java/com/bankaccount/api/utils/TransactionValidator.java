package com.bankaccount.api.utils;

import com.bankaccount.api.dto.TransactionDto;
import com.bankaccount.api.enums.Transaction;
import java.util.regex.Pattern;

public class TransactionValidator {

    private static final Pattern accountNumberPattern = Pattern.compile("^[0-9]{8}$");

    public static boolean isTransactionValid(TransactionDto transactionDto) {

        if(!Transaction.DEPOSIT.getTransaction().equals(transactionDto.getTypeTransaction()) &&
                !Transaction.WITHDRAWAL.getTransaction().equals(transactionDto.getTypeTransaction())) {
            return false;
        }
        return accountNumberPattern.matcher(transactionDto.getAccountNumber()).find();
    }
}
