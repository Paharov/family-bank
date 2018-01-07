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
        BigDecimal balance = userDao.findBalanceByName(username);
        return balance;
    }

    @Transactional(readOnly = true)
    public BigDecimal getDebtsByName(String username) {
        BigDecimal debts = userDao.findDebtsByName(username);
        return debts;
    }

    @Transactional(readOnly = true)
    public BigDecimal getAssetsByName(String username) {
        BigDecimal assets = userDao.findAssetsByName(username);
        return assets;
    }
}
