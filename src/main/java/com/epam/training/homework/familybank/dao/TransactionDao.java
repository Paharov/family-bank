package com.epam.training.homework.familybank.dao;

import com.epam.training.homework.familybank.domain.Transaction;

public interface TransactionDao {

    void save(Transaction transaction);
}
