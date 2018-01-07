package com.epam.training.homework.familybank.dao.jpaimpl;

import com.epam.training.homework.familybank.dao.UserDao;
import com.epam.training.homework.familybank.domain.Account;
import com.epam.training.homework.familybank.domain.User;

import java.math.BigDecimal;

public class JpaUserDao extends GenericJpaDao implements UserDao {

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public Account findAccountById(long accountId) {
        return entityManager.find(Account.class, accountId);
    }

    @Override
    public BigDecimal findBalanceByName(String name) {
        final String query = "select a.balance from Account a join a.user as u where u.name = :name";

        BigDecimal balance = (BigDecimal) entityManager.createQuery(query).setParameter("name", name).getSingleResult();

        return balance;
    }
}
