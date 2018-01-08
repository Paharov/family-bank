package com.epam.training.homework.familybank.dao.jpaimpl;

import com.epam.training.homework.familybank.dao.AccountDao;
import com.epam.training.homework.familybank.domain.Account;

import java.math.BigDecimal;
import java.util.List;

public class JpaAccountDao extends GenericJpaDao implements AccountDao {

    @Override
    public void save(Account account) {
        entityManager.persist(account);
    }

    @Override
    public BigDecimal getBalanceById(long accountId) {
        final String query = "select a.balance from Account a where a.id = :id";

        BigDecimal debts = entityManager.createQuery(query, BigDecimal.class)
            .setParameter("id", accountId).getSingleResult();
        return debts;
    }

    @Override
    public BigDecimal getInvestmentById(long accountId) {
        final String query = "select a.investment from Account a where a.id = :id";

        BigDecimal debts = entityManager.createQuery(query, BigDecimal.class)
            .setParameter("id", accountId).getSingleResult();
        return debts;
    }

    @Override
    public Account getAccountById(long accountId) {
        return entityManager.find(Account.class, accountId);
    }

    @Override
    public List<Account> getAccountsInDebt() {
        final String query = "select a from Account a where a.balance < 0";

        List<Account> accounts = entityManager.createQuery(query, Account.class).getResultList();

        return accounts;
    }

    @Override
    public List<Account> getAccountsWithInvestment() {
        final String query = "select a from Account a where a.investment > 0";

        List<Account> accounts = entityManager.createQuery(query, Account.class).getResultList();

        return accounts;
    }

    @Override
    public BigDecimal getSumOfDebts() {
        final String query = "select sum(a.balance) from Account a where a.balance < 0";

        return entityManager.createQuery(query, BigDecimal.class).getSingleResult();
    }

    @Override
    public BigDecimal getSumOfInvestments() {
        final String query = "select sum(a.investment) from Account a";

        return entityManager.createQuery(query, BigDecimal.class).getSingleResult();
    }

}
