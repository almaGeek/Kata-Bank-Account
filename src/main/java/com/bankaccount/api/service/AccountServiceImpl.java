package com.bankaccount.api.service;

import com.bankaccount.api.dao.AccountDao;
import com.bankaccount.api.dto.AccountDto;
import com.bankaccount.api.mapper.Mapper;
import com.bankaccount.api.model.Account;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private static final Log LOGGER = LogFactory.getLog(AccountServiceImpl.class);

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private Mapper mapper;

    @Override
    public List<AccountDto> getAll() {
        LOGGER.info("[IN] AccountServiceImpl.getAll()");
        return mapper.mapList(accountDao.findAll(), AccountDto.class);
    }

    @Override
    public AccountDto add(AccountDto account) {
        LOGGER.info("[IN] AccountServiceImpl.addOrUpdate()");
        return mapper.map(accountDao.saveAndFlush(mapper.map(account, Account.class)),AccountDto.class);
    }

    @Override
    public AccountDto update(AccountDto accountDto) {
        LOGGER.info("[IN] AccountServiceImpl.update()");
        Optional<Account> account = accountDao.findById(accountDto.getId());
        if (account.isPresent()) {
            LOGGER.info("[OUT] AccountServiceImpl.update() : " + "OK");
            return mapper.map(accountDao.saveAndFlush(mapper.map(accountDto, Account.class)),AccountDto.class);
        }
        LOGGER.info("[OUT] AccountServiceImpl.update() : " + null);
        return null;
    }

    @Override
    public boolean delete(Long id) {
        LOGGER.info("[IN] AccountServiceImpl.delete() + id = " + id);
        accountDao.deleteById(id) ;
        return true;
    }

    @Override
    public AccountDto getById(Long id) {
        LOGGER.info("[IN] AccountServiceImpl.delete() + id = " + id);
        return mapper.map(accountDao.findById(id).orElse(null), AccountDto.class);
    }

}
