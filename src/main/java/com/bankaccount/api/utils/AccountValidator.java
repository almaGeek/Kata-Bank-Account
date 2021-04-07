package com.bankaccount.api.utils;

import com.bankaccount.api.dto.AccountDto;
import com.bankaccount.api.enums.Account;
import java.util.regex.Pattern;

public class AccountValidator {

    private static final Pattern accountNumberPattern = Pattern.compile("^[0-9]{8}$");

    public static boolean isAccountValid(AccountDto accountDto) {
        if(!Account.COURANT.getType().equals(accountDto.getTypeAccount()) &&
                !Account.EPARGNE.getType().equals(accountDto.getTypeAccount())) {
            return false;
        }
        return accountNumberPattern.matcher(accountDto.getAccountNumber()).find();
    }

}
