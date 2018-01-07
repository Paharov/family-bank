package com.epam.training.homework.familybank.dao;

import com.epam.training.homework.familybank.domain.Account;
import com.epam.training.homework.familybank.domain.User;

import java.math.BigDecimal;

public interface UserDao {

    void save(User user);

    BigDecimal findBalanceByName(String name);

    BigDecimal findDebtsByName(String name);

    BigDecimal findAssetsByName(String name);

    Account findAccountByName(String name);
}
