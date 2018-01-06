package com.epam.training.homework.familybank.dao.jpaimpl;

import com.epam.training.homework.familybank.dao.AccountDao;
import com.epam.training.homework.familybank.domain.Account;

import java.math.BigDecimal;

public class JpaAccountDao extends GenericJpaDao implements AccountDao {

    @Override
    public void save(Account account) {
        entityManager.persist(account);
    }

    @Override
    public BigDecimal getDebtsById(long accountId) {
        final String query = "select a.debts from Account a where a.id = :id";

        BigDecimal debts = (BigDecimal) entityManager.createQuery(query)
            .setParameter("id", accountId).getSingleResult();
        return debts;
    }

    @Override
    public BigDecimal getAssetsById(long accountId) {
        final String query = "select a.assets from Account a where a.id = :id";

        BigDecimal assets = (BigDecimal) entityManager.createQuery(query)
            .setParameter("id", accountId).getSingleResult();
        return assets;
    }
}
