package com.epam.training.homework.familybank.dao.jpaimpl;

import com.epam.training.homework.familybank.dao.UserDao;
import com.epam.training.homework.familybank.domain.Account;
import com.epam.training.homework.familybank.domain.User;

public class JpaUserDao extends GenericJpaDao implements UserDao {

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public Account findAccountById(long accountId) {
        return entityManager.find(Account.class, accountId);
    }
}
