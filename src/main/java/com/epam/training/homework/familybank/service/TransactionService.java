package com.epam.training.homework.familybank.service;

import com.epam.training.homework.familybank.dao.AccountDao;
import com.epam.training.homework.familybank.dao.TransactionDao;
import com.epam.training.homework.familybank.dao.UserDao;
import com.epam.training.homework.familybank.domain.Account;
import com.epam.training.homework.familybank.domain.Transaction;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public class TransactionService {

    private final AccountDao accountDao;
    private final TransactionDao transactionDao;
    private final UserDao userDao;

    public TransactionService(AccountDao accountDao,
                              TransactionDao transactionDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
        this.userDao = userDao;
    }

    @Transactional
    public void giftMoney(String donor, String recipient, BigDecimal amount) {
        Account donorAccount = userDao.findAccountByName(donor);
        Account recipientAccount = userDao.findAccountByName(recipient);

        decreaseBalance(donorAccount, amount);
        increaseBalance(recipientAccount, amount);
        addTransaction(donorAccount, recipientAccount, amount);
    }

    @Transactional
    protected void decreaseBalance(Account account, BigDecimal amount) {
        BigDecimal balance = accountDao.getBalanceById(account.getId());
        if (balance.compareTo(amount) < 0) {
            throw new NotEnoughMoneyException("The donor's balance is only " + balance);
        }
        account.setBalance(balance.subtract(amount));
        accountDao.save(account);
    }

    @Transactional
    protected void increaseBalance(Account account, BigDecimal amount) {
        BigDecimal balance = accountDao.getBalanceById(account.getId());
        account.setBalance(balance.add(amount));
        accountDao.save(account);
    }

    @Transactional
    protected void addTransaction(Account from, Account to, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setFromAccount(from);
        transaction.setToAccount(to);
        transaction.setAmount(amount);
        transactionDao.save(transaction);
    }
}
