package com.bankaccount.api.service;

import com.bankaccount.api.dto.AccountDto;

import java.util.List;

public interface AccountService {

    List<AccountDto> getAll();

    AccountDto add(AccountDto account);

    boolean delete(Long id);

    AccountDto getById(Long id);

    AccountDto update(AccountDto accountDto);
}
