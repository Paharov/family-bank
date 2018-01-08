package com.epam.training.homework.familybank.service;

import com.epam.training.homework.familybank.dao.AccountDao;
import com.epam.training.homework.familybank.dao.UserDao;
import com.epam.training.homework.familybank.domain.Account;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import javax.annotation.Resource;

public class AccountOverviewService {

    private final AccountDao accountDao;
    private final UserDao userDao;

    @Resource
    private Account bank;

    public AccountOverviewService(AccountDao accountDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @Transactional(readOnly = true)
    public BigDecimal getBalanceByName(String username) {
        BigDecimal balance = userDao.findBalanceByName(username);
        return balance;
    }

    @Transactional(readOnly = true)
    public BigDecimal getBankBalance() {
        return accountDao.getBalanceById(bank.getId());
    }

}
