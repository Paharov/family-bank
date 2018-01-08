package com.epam.training.homework.familybank.service;

import com.epam.training.homework.familybank.dao.AccountDao;
import com.epam.training.homework.familybank.dao.TransactionDao;
import com.epam.training.homework.familybank.dao.UserDao;
import com.epam.training.homework.familybank.domain.Account;
import com.epam.training.homework.familybank.domain.Transaction;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public class TransactionService {

    private static final BigDecimal LENDING_RATE = new BigDecimal(0.001);
    private static final BigDecimal BORROWING_RATE = new BigDecimal(0.002);

    private final AccountDao accountDao;
    private final TransactionDao transactionDao;
    private final UserDao userDao;
    private final Account bank;

    public TransactionService(AccountDao accountDao,
                              TransactionDao transactionDao, UserDao userDao, Account bank) {
        this.accountDao = accountDao;
        this.transactionDao = transactionDao;
        this.userDao = userDao;
        this.bank = bank;
    }

    @Transactional
    public void gift(String donor, String recipient, BigDecimal amount) {
        if (amount.compareTo(new BigDecimal(0)) <= 0) {
            throw new InvalidAmountException("You can gift only a positive amount!");
        }

        Account donorAccount = userDao.findAccountByName(donor);
        Account recipientAccount = userDao.findAccountByName(recipient);

        BigDecimal donorBalance = accountDao.getBalanceById(donorAccount.getId());
        if (donorBalance.compareTo(amount) < 0) {
            throw new NotEnoughMoneyException("The donor's balance is only " + donorBalance);
        }

        decreaseBalance(donorAccount, amount);
        increaseBalance(recipientAccount, amount);
        addTransaction(donorAccount, recipientAccount, amount);
    }

    @Transactional
    public void deposit(String name, BigDecimal amount) {
        if (amount.compareTo(new BigDecimal(0)) <= 0) {
            throw new InvalidAmountException("You can deposit only a positive amount!");
        }

        Account account = userDao.findAccountByName(name);
        increaseBalance(account, amount);
    }

    @Transactional
    public void withdraw(String name, BigDecimal amount) {
        if (amount.compareTo(new BigDecimal(0)) <= 0) {
            throw new InvalidAmountException("You can withdraw only a positive amount!");
        }

        Account account = userDao.findAccountByName(name);
        BigDecimal balance = accountDao.getBalanceById(account.getId());
        if (balance.compareTo(amount) < 0) {
            throw new NotEnoughMoneyException("Your balance is only " + balance);
        }
        decreaseBalance(account, amount);
    }

    @Transactional
    public void lend(String name, BigDecimal amount) {
        Account account = userDao.findAccountByName(name);
        BigDecimal balance = accountDao.getBalanceById(account.getId());
        if (balance.compareTo(amount) < 0) {
            throw new NotEnoughMoneyException("Your balance in only " + balance);
        }
        Account bankEntity = accountDao.getAccountById(bank.getId());
        decreaseBalance(account, amount);
        increaseInvestment(account, amount);
        increaseBalance(bankEntity, amount);
        addTransaction(account, bankEntity, amount);
    }


    @Transactional
    public void borrow(String name, BigDecimal amount) {
        Account account = userDao.findAccountByName(name);
        Account bankEntity = accountDao.getAccountById(bank.getId());
        decreaseBalance(account, amount);
        decreaseBalance(bankEntity, amount);
        addTransaction(bankEntity, account, amount);
    }

    @Transactional
    protected void decreaseBalance(Account account, BigDecimal amount) {
        BigDecimal balance = accountDao.getBalanceById(account.getId());
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
    protected void increaseInvestment(Account account, BigDecimal amount) {
        BigDecimal investment = accountDao.getInvestmentById(account.getId());
        account.setInvestment(investment.add(amount));
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

    @Transactional
    public void calculateInterests() {
        updateDebts();
        dealProfit();
    }

    @Transactional
    protected void updateDebts() {
        List<Account> accountsInDebt = accountDao.getAccountsInDebt();
        for (Account account : accountsInDebt) {
            BigDecimal balance = accountDao.getBalanceById(account.getId());
            account.setBalance(balance.add(balance.multiply(BORROWING_RATE)));
            accountDao.save(account);
        }
    }

    @Transactional
    protected void dealProfit() {
        BigDecimal sumOfDebts = accountDao.getSumOfDebts();
        BigDecimal sumOfInvestments = accountDao.getSumOfInvestments();
        List<Account> accountsWithInvestment = accountDao.getAccountsWithInvestment();
        for (Account account : accountsWithInvestment) {
            BigDecimal balance = accountDao.getBalanceById(account.getId());
            BigDecimal investment = accountDao.getInvestmentById(account.getId());
            BigDecimal investmentRatio = investment.divide(sumOfInvestments);
            account.setBalance(balance.add(sumOfDebts.multiply(investmentRatio).multiply(LENDING_RATE)));
            account.setBalance(balance.add(investment.multiply(BORROWING_RATE.subtract(LENDING_RATE))));
            accountDao.save(account);
        }
    }

}
