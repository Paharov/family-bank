package com.epam.training.homework.familybank.service;

import com.epam.training.homework.familybank.dao.AccountDao;
import com.epam.training.homework.familybank.dao.TransactionDao;
import com.epam.training.homework.familybank.dao.UserDao;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public class AccountOverviewService {

    private final AccountDao accountDao;
    private final TransactionDao transactionDao;
    private final UserDao userDao;

    public AccountOverviewService(AccountDao accountDao, TransactionDao transactionDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
        this.userDao = userDao;
    }

    @Transactional(readOnly = true)
    public BigDecimal getBalanceByName(String username) {
        return new BigDecimal(444);
    }
}
