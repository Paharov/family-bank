package com.epam.training.homework.familybank.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

import com.epam.training.homework.familybank.dao.AccountDao;
import com.epam.training.homework.familybank.dao.TransactionDao;
import com.epam.training.homework.familybank.dao.UserDao;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.math.BigDecimal;
import javax.persistence.NoResultException;

public class AccountOverviewServiceTest {

    private AccountDao accountDao;
    private TransactionDao transactionDao;
    private UserDao userDao;
    private AccountOverviewService underTest;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        accountDao = Mockito.mock(AccountDao.class);
        transactionDao = Mockito.mock(TransactionDao.class);
        userDao = Mockito.mock(UserDao.class);
        underTest = new AccountOverviewService(accountDao, transactionDao, userDao);
    }

    @Test
    public void shouldReturnWithBalanceResultWhenExistingUser() {
        // Given
        Mockito.when(userDao.findBalanceByName("ExistingUser")).thenReturn(new BigDecimal(2000));

        // When
        BigDecimal balance = underTest.getBalanceByName("ExistingUser");

        // Then
        assertThat(balance, is(equalTo(new BigDecimal(2000))));
    }

    @Test
    public void shouldReturnWithDebtsResultWhenExistingUser() {
        // Given
        Mockito.when(userDao.findDebtsByName("ExistingUser")).thenReturn(new BigDecimal(100));

        // When
        BigDecimal debts = underTest.getDebtsByName("ExistingUser");

        // Then
        assertThat(debts, is(equalTo(new BigDecimal(100))));
    }

    @Test
    public void shouldReturnWithAssetsResultWhenExistingUser() {
        // Given
        Mockito.when(userDao.findAssetsByName("ExistingUser")).thenReturn(new BigDecimal(100));

        // When
        BigDecimal assets = underTest.getAssetsByName("ExistingUser");

        // Then
        assertThat(assets, is(equalTo(new BigDecimal(100))));
    }

    @Test
    public void shouldThrowNoResultExceptionWhenAskedForBalanceOfNonExistingUser() {
        // Given
        exception.expect(NoResultException.class);
        Mockito.when(userDao.findBalanceByName("NonExistingUser")).thenThrow(new NoResultException());

        // When
        underTest.getBalanceByName("NonExistingUser");

        // Then
        fail("Should have thrown a NoResultException!");
    }

    @Test
    public void shouldThrowNoResultExceptionWhenAskedForDebtsOfNonExistingUser() {
        // Given
        exception.expect(NoResultException.class);
        Mockito.when(userDao.findDebtsByName("NonExistingUser")).thenThrow(new NoResultException());

        // When
        underTest.getDebtsByName("NonExistingUser");

        // Then
        fail("Should have thrown a NoResultException!");
    }

    @Test
    public void shouldThrowNoResultExceptionWhenAskedForAssetsOfNonExistingUser() {
        // Given
        exception.expect(NoResultException.class);
        Mockito.when(userDao.findAssetsByName("NonExistingUser")).thenThrow(new NoResultException());

        // When
        underTest.getAssetsByName("NonExistingUser");

        // Then
        fail("Should have thrown a NoResultException!");
    }
}
