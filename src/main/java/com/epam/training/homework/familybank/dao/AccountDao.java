package com.epam.training.homework.familybank.dao;

import com.epam.training.homework.familybank.domain.Transaction;

import java.math.BigDecimal;

public interface AccountDao {

    void addTransaction(Transaction transaction);

    void updateDebts(BigDecimal newDebts);

    void updateAssets(BigDecimal newAssets);

    BigDecimal getDebts();

    BigDecimal getAssets();
}
