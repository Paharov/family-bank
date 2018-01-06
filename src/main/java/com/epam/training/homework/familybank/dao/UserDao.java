package com.epam.training.homework.familybank.dao;

import com.epam.training.homework.familybank.domain.Account;
import com.epam.training.homework.familybank.domain.User;

public interface UserDao {

    void save(User user);

    Account findAccountById(long accountId);
}
