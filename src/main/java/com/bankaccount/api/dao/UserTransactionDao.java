package com.bankaccount.api.dao;

import com.bankaccount.api.model.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTransactionDao extends JpaRepository<UserTransaction,Long> {
}
