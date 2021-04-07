package com.bankaccount.api.service;

import com.bankaccount.api.dao.AccountDao;
import com.bankaccount.api.dao.UserTransactionDao;
import com.bankaccount.api.dto.TransactionDto;
import com.bankaccount.api.enums.Transaction;
import com.bankaccount.api.mapper.Mapper;
import com.bankaccount.api.model.Account;
import com.bankaccount.api.model.UserTransaction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserTransactionServiceImpl implements UserTransactionService{

    private static final Log LOGGER = LogFactory.getLog(UserTransactionServiceImpl.class);

    @Autowired
    private UserTransactionDao userTransactionDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private Mapper mapper;

    @Override
    public boolean addTransaction(TransactionDto transactionDto) {
        LOGGER.info("[IN] UserTransactionServiceImpl.addTransaction()");
        Optional<Account> targetAccount = accountDao.findByAccountNumber(transactionDto.getAccountNumber());
        if (targetAccount.isPresent()) {
            return makePayement(transactionDto,targetAccount.get());
        }
        LOGGER.info("[OUT] UserTransactionServiceImpl.addTransaction() + " + false);
        return false;
    }

    @Override
    public List<TransactionDto> getAccountTransactions(Long id) {
        LOGGER.info("[IN] UserTransactionServiceImpl.addTransaction()");
        Optional<Account> account = accountDao.findById(id);
        if (account.isPresent()) {
            LOGGER.info("[OUT] UserTransactionServiceImpl.addTransaction() : " + "OK");
            return mapper.mapList(account.get().getUserTransactionList(), TransactionDto.class);
        }
        LOGGER.info("[OUT] UserTransactionServiceImpl.addTransaction() : " + null);
        return null;
    }

    @Override
    public List<TransactionDto> getHistoriqueTransactions() {
       return mapper.mapList(userTransactionDao.findAll(), TransactionDto.class);
    }

    private boolean makePayement(TransactionDto transactionDto,Account targetAccount) {
        LOGGER.info("[IN] UserTransactionServiceImpl.makePayement()");
        final double maxTransaction = targetAccount.getMaxTransaction();
        double currentBalance = targetAccount.getCurrentBalance();
        double amount = transactionDto.getAmount();

        if (Transaction.DEPOSIT.getTransaction().equals(transactionDto.getTypeTransaction()) &&
        isValidAmount(currentBalance,amount,maxTransaction,Transaction.DEPOSIT.getTransaction())) {
            targetAccount.setCurrentBalance(currentBalance+transactionDto.getAmount());
        } else if (Transaction.WITHDRAWAL.getTransaction().equals(transactionDto.getTypeTransaction()) &&
                isValidAmount(currentBalance,amount,maxTransaction,Transaction.WITHDRAWAL.getTransaction())) {
            targetAccount.setCurrentBalance(currentBalance-transactionDto.getAmount());
        } else {
            LOGGER.info("[OUT] UserTransactionServiceImpl.makePayement() + " + false);
            return false;
        }
        LOGGER.info("[OUT] UserTransactionServiceImpl.save() + " + true);
        return save(transactionDto, targetAccount);
    }

    private boolean save(TransactionDto transactionDto, Account targetAccount) {
        LOGGER.info("[IN] UserTransactionServiceImpl.save()");
        accountDao.save(targetAccount);
        UserTransaction ut = mapper.map(transactionDto, UserTransaction.class);
        ut.setTargetAccount(targetAccount);
        ut.setDateTransaction(LocalDateTime.now());
        userTransactionDao.save(ut);
        return true;
    }

    private boolean isValidAmount(double currentBalance, double amount, double maxTransaction, String transaction) {
        LOGGER.info("[IN] UserTransactionServiceImpl.isValidAmount()");
        return amount <= maxTransaction && (Transaction.DEPOSIT.getTransaction().equals(transaction) ||
                (Transaction.WITHDRAWAL.getTransaction().equals(transaction) && currentBalance >= amount)) ;
    }
}
