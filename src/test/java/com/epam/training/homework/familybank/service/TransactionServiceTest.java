package com.epam.training.homework.familybank.service;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;

import com.epam.training.homework.familybank.dao.AccountDao;
import com.epam.training.homework.familybank.dao.TransactionDao;
import com.epam.training.homework.familybank.dao.UserDao;
import com.epam.training.homework.familybank.domain.Account;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.math.BigDecimal;

public class TransactionServiceTest {

    private AccountDao accountDao;
    private TransactionDao transactionDao;
    private UserDao userDao;
    private TransactionService underTest;
    private Account bank;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        accountDao = Mockito.mock(AccountDao.class);
        transactionDao = Mockito.mock(TransactionDao.class);
        userDao = Mockito.mock(UserDao.class);
        bank = Mockito.mock(Account.class);
        underTest = new TransactionService(accountDao, transactionDao, userDao, bank);
    }

    @Test
    public void giftShouldExecuteGiftingWhenCalledWithSufficientBalance() {
        // Given
        Mockito.when(userDao.findAccountByName("From")).thenReturn(new Account());
        Mockito.when(userDao.findAccountByName("To")).thenReturn(new Account());
        Mockito.when(accountDao.getBalanceById(Mockito.anyLong())).thenReturn(new BigDecimal(10000));

        // When
        underTest.gift("From", "To", new BigDecimal(100));

        // Then
        InOrder inOrder = Mockito.inOrder(userDao, accountDao, transactionDao);
        inOrder.verify(userDao).findAccountByName("From");
        inOrder.verify(userDao).findAccountByName("To");
        inOrder.verify(accountDao, times(2)).getBalanceById(Mockito.anyLong());
        inOrder.verify(accountDao).save(Mockito.anyObject());
        inOrder.verify(accountDao).getBalanceById(Mockito.anyLong());
        inOrder.verify(accountDao).save(Mockito.anyObject());
        inOrder.verify(transactionDao).save(Mockito.anyObject());
    }

    @Test
    public void giftShouldThrowNotEnoughMoneyExceptionWhenGiftingCalledWithInsufficientBalance() {
        // Given
        Mockito.when(userDao.findAccountByName("From")).thenReturn(new Account());
        Mockito.when(userDao.findAccountByName("To")).thenReturn(new Account());
        Mockito.when(accountDao.getBalanceById(Mockito.anyLong())).thenReturn(new BigDecimal(50));
        exception.expect(NotEnoughMoneyException.class);
        exception.expectMessage("The donor's balance is only 50");

        // When
        underTest.gift("From", "To", new BigDecimal(100));

        // Then
        fail("Should have thrown a NotEnoughMoneyException!");
    }

    @Test
    public void giftShouldThrowInvalidAmountExceptionWhenGiftNegativeAmount() {
        // Given
        Mockito.when(userDao.findAccountByName("From")).thenReturn(new Account());
        Mockito.when(userDao.findAccountByName("To")).thenReturn(new Account());
        exception.expect(InvalidAmountException.class);
        exception.expectMessage("You can gift only a positive amount!");

        // When
        underTest.gift("From", "To", new BigDecimal(-100));

        // Then
        fail("Should have thrown an InvalidAmountException");
    }

    @Test
    public void giftShouldThrowInvalidAmountExceptionWhenGiftZero() {
        // Given
        Mockito.when(userDao.findAccountByName("From")).thenReturn(new Account());
        Mockito.when(userDao.findAccountByName("To")).thenReturn(new Account());
        exception.expect(InvalidAmountException.class);
        exception.expectMessage("You can gift only a positive amount!");

        // When
        underTest.gift("From", "To", new BigDecimal(0));

        // Then
        fail("Should have thrown an InvalidAmountException");
    }

    @Test
    public void depositShouldIncreaseBalanceWhenDepositPositiveAmount() {
        // Given
        Mockito.when(userDao.findAccountByName(Mockito.anyString())).thenReturn(new Account());
        Mockito.when(accountDao.getBalanceById(Mockito.anyLong())).thenReturn(new BigDecimal(Mockito.anyInt()));

        // When
        underTest.deposit("Anyone", new BigDecimal(100));

        // Then
        InOrder inOrder = Mockito.inOrder(userDao, accountDao);
        inOrder.verify(userDao).findAccountByName("Anyone");
        inOrder.verify(accountDao).getBalanceById(Mockito.anyLong());
        inOrder.verify(accountDao).save(Mockito.anyObject());
    }

    @Test
    public void depositShouldThrowInvalidAmountExceptionWhenDepositNegativeAmount() {
        // Given
        Mockito.when(userDao.findAccountByName(Mockito.anyString())).thenReturn(new Account());
        exception.expect(InvalidAmountException.class);
        exception.expectMessage("You can deposit only a positive amount!");

        // When
        underTest.deposit("Anyone", new BigDecimal(-100));

        // Then
        fail("Should have thrown an InvalidAmountException");
    }

    @Test
    public void depositShouldThrowInvalidAmountExceptionWhenDepositZero() {
        // Given
        Mockito.when(userDao.findAccountByName(Mockito.anyString())).thenReturn(new Account());
        exception.expect(InvalidAmountException.class);
        exception.expectMessage("You can deposit only a positive amount!");

        // When
        underTest.deposit("Anyone", new BigDecimal(0));

        // Then
        fail("Should have thrown an InvalidAmountException");
    }

    @Test
    public void withdrawShouldDecreaseBalanceWhenEnoughMoney() {
        // Given
        Mockito.when(userDao.findAccountByName(Mockito.anyString())).thenReturn(new Account());
        Mockito.when(accountDao.getBalanceById(Mockito.anyLong())).thenReturn(new BigDecimal(1000));

        // When
        underTest.withdraw("Anyone", new BigDecimal(100));

        // Then
        InOrder inOrder = Mockito.inOrder(userDao, accountDao);
        inOrder.verify(userDao).findAccountByName("Anyone");
        inOrder.verify(accountDao, times(2)).getBalanceById(Mockito.anyLong());
        inOrder.verify(accountDao).save(Mockito.anyObject());
    }

    @Test
    public void withdrawShouldThrowNotEnoughMoneyExceptionWhenNotEnoughMoney() {
        // Given
        Mockito.when(userDao.findAccountByName(Mockito.anyString())).thenReturn(new Account());
        Mockito.when(accountDao.getBalanceById(Mockito.anyLong())).thenReturn(new BigDecimal(50));
        exception.expect(NotEnoughMoneyException.class);
        exception.expectMessage("Your balance is only 50");

        // When
        underTest.withdraw("Anyone", new BigDecimal(100));

        // Then
        InOrder inOrder = Mockito.inOrder(userDao, accountDao);
        inOrder.verify(userDao).findAccountByName("Anyone");
        inOrder.verify(accountDao).getBalanceById(Mockito.anyLong());
        inOrder.verify(accountDao).save(Mockito.anyObject());
    }

    @Test
    public void withdrawShouldThrowInvalidAmountExceptionWhenNegativeAmount() {
        // Given
        Mockito.when(userDao.findAccountByName(Mockito.anyString())).thenReturn(new Account());
        exception.expect(InvalidAmountException.class);
        exception.expectMessage("You can withdraw only a positive amount!");

        // When
        underTest.withdraw("Anyone", new BigDecimal(-100));

        // Then
        fail("Should have thrown an InvalidAmountException!");
    }

    @Test
    public void lendShouldExecuteWhenCalledWithEnoughMoney() {
        // Given
        Mockito.when(userDao.findAccountByName(Mockito.anyString())).thenReturn(new Account());
        Mockito.when(accountDao.getBalanceById(Mockito.anyLong())).thenReturn(new BigDecimal(1000));
        Mockito.when(accountDao.getAccountById(Mockito.anyLong())).thenReturn(new Account());
        Mockito.when(accountDao.getInvestmentById(Mockito.anyLong())).thenReturn(new BigDecimal(Mockito.anyInt()));

        // When
        underTest.lend("Anyone", new BigDecimal(100));

        // Then
        InOrder inOrder = Mockito.inOrder(userDao, accountDao, transactionDao);
        inOrder.verify(userDao).findAccountByName("Anyone");
        inOrder.verify(accountDao).getBalanceById(Mockito.anyLong());
        inOrder.verify(accountDao).getAccountById(Mockito.anyLong());
        inOrder.verify(accountDao).getBalanceById(Mockito.anyLong());
        inOrder.verify(accountDao).save(Mockito.anyObject());
        inOrder.verify(accountDao).getInvestmentById(Mockito.anyLong());
        inOrder.verify(accountDao).save(Mockito.anyObject());
        inOrder.verify(accountDao).getBalanceById(Mockito.anyLong());
        inOrder.verify(accountDao).save(Mockito.anyObject());
        inOrder.verify(transactionDao).save(Mockito.anyObject());
    }

    @Test
    public void lendShouldThrowNotEnoughMoneyExceptionWhenCalledWithNotEnoughMoney() {
        // Given
        Mockito.when(userDao.findAccountByName(Mockito.anyString())).thenReturn(new Account());
        Mockito.when(accountDao.getBalanceById(Mockito.anyLong())).thenReturn(new BigDecimal(50));
        Mockito.when(accountDao.getAccountById(Mockito.anyLong())).thenReturn(new Account());
        exception.expect(NotEnoughMoneyException.class);
        exception.expectMessage("Your balance in only 50");

        // When
        underTest.lend("Anyone", new BigDecimal(100));

        // Then
        fail("Should have thrown a NotEnoughMoneyException!");
    }

    @Test
    public void borrowShouldDecreaseBalancesWhenCalledWithPositiveMoney() {
        // Given
        Mockito.when(userDao.findAccountByName(Mockito.anyString())).thenReturn(new Account());
        Mockito.when(accountDao.getBalanceById(Mockito.anyLong())).thenReturn(new BigDecimal(100));
        Mockito.when(accountDao.getAccountById(Mockito.anyLong())).thenReturn(new Account());

        // When
        underTest.borrow("Anyone", new BigDecimal(1000));

        // Then
        InOrder inOrder = Mockito.inOrder(userDao, accountDao, transactionDao);
        inOrder.verify(userDao).findAccountByName("Anyone");
        inOrder.verify(accountDao).getAccountById(Mockito.anyLong());
        inOrder.verify(accountDao).getBalanceById(Mockito.anyLong());
        inOrder.verify(accountDao).save(Mockito.anyObject());
        inOrder.verify(accountDao).getBalanceById(Mockito.anyLong());
        inOrder.verify(accountDao).save(Mockito.anyObject());
        inOrder.verify(transactionDao).save(Mockito.anyObject());
    }

    @Test
    public void borrowShouldDecreaseBalancesWhenCalledWithNegativeMoney() {
        // Given
        Mockito.when(userDao.findAccountByName(Mockito.anyString())).thenReturn(new Account());
        Mockito.when(accountDao.getBalanceById(Mockito.anyLong())).thenReturn(new BigDecimal(-1000));
        Mockito.when(accountDao.getAccountById(Mockito.anyLong())).thenReturn(new Account());

        // When
        underTest.borrow("Anyone", new BigDecimal(1000));

        // Then
        InOrder inOrder = Mockito.inOrder(userDao, accountDao, transactionDao);
        inOrder.verify(userDao).findAccountByName("Anyone");
        inOrder.verify(accountDao).getAccountById(Mockito.anyLong());
        inOrder.verify(accountDao).getBalanceById(Mockito.anyLong());
        inOrder.verify(accountDao).save(Mockito.anyObject());
        inOrder.verify(accountDao).getBalanceById(Mockito.anyLong());
        inOrder.verify(accountDao).save(Mockito.anyObject());
        inOrder.verify(transactionDao).save(Mockito.anyObject());
    }
}
