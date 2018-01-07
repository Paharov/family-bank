package com.epam.training.homework.familybank.service;

import static org.junit.Assert.fail;

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

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        accountDao = Mockito.mock(AccountDao.class);
        transactionDao = Mockito.mock(TransactionDao.class);
        userDao = Mockito.mock(UserDao.class);
        underTest = new TransactionService(accountDao, transactionDao, userDao);
    }

    @Test
    public void shouldExecuteTheTransferWhenCalledWithSufficientBalance() {
        // Given
        Mockito.when(userDao.findAccountByName("From")).thenReturn(new Account());
        Mockito.when(userDao.findAccountByName("To")).thenReturn(new Account());
        Mockito.when(accountDao.getBalanceById(Mockito.anyLong())).thenReturn(new BigDecimal(10000));

        // When
        underTest.giftMoney("From", "To", new BigDecimal(100));

        // Then
        InOrder inOrder = Mockito.inOrder(userDao, accountDao, transactionDao);
        inOrder.verify(userDao).findAccountByName("From");
        inOrder.verify(userDao).findAccountByName("To");
        inOrder.verify(accountDao).getBalanceById(Mockito.anyLong());
        inOrder.verify(accountDao).save(Mockito.anyObject());
        inOrder.verify(accountDao).getBalanceById(Mockito.anyLong());
        inOrder.verify(accountDao).save(Mockito.anyObject());
        inOrder.verify(transactionDao).save(Mockito.anyObject());
    }

    @Test
    public void shouldThrowNotEnoughMoneyExceptionWhenCalledWithInsufficientBalance() {
        // Given
        Mockito.when(userDao.findAccountByName("From")).thenReturn(new Account());
        Mockito.when(userDao.findAccountByName("To")).thenReturn(new Account());
        Mockito.when(accountDao.getBalanceById(Mockito.anyLong())).thenReturn(new BigDecimal(50));
        exception.expect(NotEnoughMoneyException.class);
        exception.expectMessage("The donor's balance is only 50");

        // When
        underTest.giftMoney("From", "To", new BigDecimal(100));

        // Then
        fail("Should have thrown a NotEnoughMoneyException!");
    }
}
