package com.epam.training.homework.familybank.dao;

import com.epam.training.homework.familybank.domain.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    void save(Account account);

    BigDecimal getBalanceById(long accountId);

    BigDecimal getInvestmentById(long accountId);

    Account getAccountById(long accountId);

    List<Account> getAccountsInDebt();

    List<Account> getAccountsWithInvestment();

    BigDecimal getSumOfDebts();

    BigDecimal getSumOfInvestments();
}
