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
    public BigDecimal findBalanceByName(String name) {
        final String query = "select a.balance from Account a join a.user as u where u.name = :name";

        return entityManager.createQuery(query, BigDecimal.class).setParameter("name", name).getSingleResult();
    }

    @Override
    public Account findAccountByName(String name) {
        final String query = "select a from Account a join a.user as u where u.name = :name";

        return entityManager.createQuery(query, Account.class).setParameter("name", name).getSingleResult();
    }
}
