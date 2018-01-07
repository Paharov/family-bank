package com.epam.training.homework.familybank.dao;

import com.epam.training.homework.familybank.domain.Account;

import java.math.BigDecimal;

public interface AccountDao {

    void save(Account account);

    BigDecimal getBalanceById(long accountId);

    BigDecimal getDebtsById(long accountId);

    BigDecimal getAssetsById(long accountId);
}
