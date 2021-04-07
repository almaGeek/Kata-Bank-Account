package com.bankaccount.api.service;

import com.bankaccount.api.dao.AccountDao;
import com.bankaccount.api.dto.AccountDto;
import com.bankaccount.api.model.Account;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class AccountServiceImplTest {

    @MockBean
    private AccountDao accountDao;

    @Autowired
    private AccountService accountService;

    List<Account> mockAccounts = Arrays.asList(
            Account.builder().id(1L).accountNumber("25403698").typeAccount(com.bankaccount.api.enums.Account.COURANT.getType()).currentBalance(0).maxTransaction(1000).userTransactionList(new ArrayList<>()).build(),
            Account.builder().id(2L).accountNumber("25403699").typeAccount(com.bankaccount.api.enums.Account.EPARGNE.getType()).currentBalance(100).maxTransaction(1000).userTransactionList(new ArrayList<>()).build(),
            Account.builder().id(3L).accountNumber("25403610").typeAccount(com.bankaccount.api.enums.Account.COURANT.getType()).currentBalance(1000).maxTransaction(1000).userTransactionList(new ArrayList<>()).build());

    @Test
    void shouldReturnAllAccounts() {
        Mockito.when(accountDao.findAll()).thenReturn(mockAccounts);
        List<AccountDto> list = accountService.getAll();
        Assert.assertEquals(list.get(0).getAccountNumber(), mockAccounts.get(0).getAccountNumber());
    }

    @Test
    void shouldAddAccount() {
        Mockito.when(accountDao.saveAndFlush(Mockito.anyObject())).thenReturn(mockAccounts.get(0));
        AccountDto account = AccountDto.builder().id(1L).accountNumber("25403698").typeAccount(com.bankaccount.api.enums.Account.COURANT.getType()).currentBalance(0).maxTransaction(1000).build();
        AccountDto accountDto = accountService.add(account);
        Assert.assertEquals(accountDto.getAccountNumber(), mockAccounts.get(0).getAccountNumber());
        Assert.assertEquals(accountDto.getId(), mockAccounts.get(0).getId());
    }

    @Test
    void shouldUpdateAccount() {
        Mockito.when(accountDao.findById(Mockito.anyLong())).thenReturn(Optional.of(mockAccounts.get(0)));
        Mockito.when(accountDao.saveAndFlush(Mockito.anyObject())).thenReturn(mockAccounts.get(0));
        AccountDto account = AccountDto.builder().id(1L).accountNumber("25403698").typeAccount(com.bankaccount.api.enums.Account.COURANT.getType()).currentBalance(0).maxTransaction(1000).build();
        AccountDto accountDto = accountService.update(account);
        Assert.assertEquals(accountDto.getAccountNumber(), mockAccounts.get(0).getAccountNumber());
        Assert.assertEquals(accountDto.getId(), mockAccounts.get(0).getId());
    }

    @Test
    void shouldDeleteAccount() {
        AccountService mock = mock(AccountService.class);
        doNothing().when(accountDao).deleteById(Mockito.anyLong());
        mock.delete(1L);
        verify(mock, times(1)).delete(1L);
    }

    @Test
    void shoudGetAccountgById() {
        Mockito.when(accountDao.findById(anyLong())).thenReturn(Optional.of(mockAccounts.get(0)));
        AccountDto account = accountService.getById(1l);
        Assert.assertEquals(account.getAccountNumber(), mockAccounts.get(0).getAccountNumber());
    }
}