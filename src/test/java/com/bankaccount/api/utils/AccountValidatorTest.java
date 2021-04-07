package com.bankaccount.api.utils;

import com.bankaccount.api.dto.AccountDto;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
class AccountValidatorTest {

    @Autowired
    private AccountValidator accountValidator;

    @Test
    void shouldReturnAccountInValidWhenTypeAccountIsIncorrect() {
        AccountDto a1 = AccountDto.builder().typeAccount("courant").accountNumber("147852369").currentBalance(100).maxTransaction(1000).build();
        AccountDto a2 = AccountDto.builder().typeAccount("Epargn").accountNumber("0123456").currentBalance(0).maxTransaction(500).build();
        Assert.assertFalse(accountValidator.isAccountValid(a1));
        Assert.assertFalse(accountValidator.isAccountValid(a2));
    }

    @Test
    void shouldReturnAccountInValidWhenAccountNumberIsIncorrect() {
        AccountDto a3 = AccountDto.builder().typeAccount("Epargne").accountNumber("012345").currentBalance(1000).maxTransaction(1000).build();
        AccountDto a4 = AccountDto.builder().typeAccount("Epargne").accountNumber("0a125b58").currentBalance(1000).maxTransaction(1000).build();
        Assert.assertFalse(accountValidator.isAccountValid(a3));
        Assert.assertFalse(accountValidator.isAccountValid(a4));
    }
}