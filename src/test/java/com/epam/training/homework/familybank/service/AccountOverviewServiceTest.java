package com.epam.training.homework.familybank.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

import com.epam.training.homework.familybank.dao.AccountDao;
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
    private UserDao userDao;
    private AccountOverviewService underTest;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUp() {
        accountDao = Mockito.mock(AccountDao.class);
        userDao = Mockito.mock(UserDao.class);
        underTest = new AccountOverviewService(accountDao, userDao);
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
    public void shouldThrowNoResultExceptionWhenAskedForBalanceOfNonExistingUser() {
        // Given
        exception.expect(NoResultException.class);
        Mockito.when(userDao.findBalanceByName("NonExistingUser")).thenThrow(new NoResultException());

        // When
        underTest.getBalanceByName("NonExistingUser");

        // Then
        fail("Should have thrown a NoResultException!");
    }
}
