package com.epam.training.homework.familybank.dao.jpaimpl;

import com.epam.training.homework.familybank.dao.TransactionDao;
import com.epam.training.homework.familybank.domain.Transaction;

public class JpaTransactionDao extends GenericJpaDao implements TransactionDao {

    @Override
    public void save(Transaction transaction) {
        entityManager.persist(transaction);
    }
}
