package com.bankaccount.api;

import com.bankaccount.api.dao.AccountDao;
import com.bankaccount.api.dao.UserTransactionDao;
import com.bankaccount.api.model.Account;
import com.bankaccount.api.model.UserTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class KataBankAccountApisApplication implements CommandLineRunner {

/*	@Autowired
	private UserTransactionDao userTransactionDao;

	@Autowired
	private AccountDao accountDao;*/

	public static void main(String[] args) {
		SpringApplication.run(KataBankAccountApisApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
/*		List<Account> accounts = accountDao.saveAll(Arrays.asList(
				Account.builder().id(1L).accountNumber("00000001").typeAccount("Courant").currentBalance(0.0).maxTransaction(1000.0).build(),
				Account.builder().id(2L).accountNumber("00000002").typeAccount("Epargne").currentBalance(0.0).maxTransaction(500.0).build(),
				Account.builder().id(3L).accountNumber("00000003").typeAccount("Courant").currentBalance(0.0).maxTransaction(1000.0).build(),
				Account.builder().id(4L).accountNumber("00000004").typeAccount("Epargne").currentBalance(0.0).maxTransaction(500.0).build(),
				Account.builder().id(5L).accountNumber("00000005").typeAccount("Courant").currentBalance(0.0).maxTransaction(1000.0).build()
		));

		userTransactionDao.saveAll(Arrays.asList(
				UserTransaction.builder().id(10L).typeTransaction("Deposit").amount(500.0).dateTransaction(LocalDateTime.now()).targetAccount(accounts.get(0)).build(),
				UserTransaction.builder().id(11L).typeTransaction("Withdrawal").amount(20.0).dateTransaction(LocalDateTime.now()).targetAccount(accounts.get(0)).build(),
				UserTransaction.builder().id(12L).typeTransaction("Deposit").amount(100.0).dateTransaction(LocalDateTime.now()).targetAccount(accounts.get(0)).build(),
				UserTransaction.builder().id(13L).typeTransaction("Withdrawal").amount(50.0).dateTransaction(LocalDateTime.now()).targetAccount(accounts.get(1)).build(),
				UserTransaction.builder().id(14L).typeTransaction("Deposit").amount(1000.0).dateTransaction(LocalDateTime.now()).targetAccount(accounts.get(1)).build(),
				UserTransaction.builder().id(15L).typeTransaction("Deposit").amount(500.0).dateTransaction(LocalDateTime.now()).targetAccount(accounts.get(2)).build(),
				UserTransaction.builder().id(16L).typeTransaction("Withdrawal").amount(20.0).dateTransaction(LocalDateTime.now()).targetAccount(accounts.get(3)).build(),
				UserTransaction.builder().id(17L).typeTransaction("Deposit").amount(100.0).dateTransaction(LocalDateTime.now()).targetAccount(accounts.get(3)).build(),
				UserTransaction.builder().id(18L).typeTransaction("Withdrawal").amount(50.0).dateTransaction(LocalDateTime.now()).targetAccount(accounts.get(4)).build(),
				UserTransaction.builder().id(19L).typeTransaction("Deposit").amount(1000.0).dateTransaction(LocalDateTime.now()).targetAccount(accounts.get(4)).build()
		));*/
	}
}
