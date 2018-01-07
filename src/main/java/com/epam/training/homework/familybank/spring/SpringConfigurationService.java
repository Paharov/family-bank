package com.epam.training.homework.familybank.spring;

import com.epam.training.homework.familybank.dao.AccountDao;
import com.epam.training.homework.familybank.dao.TransactionDao;
import com.epam.training.homework.familybank.dao.UserDao;
import com.epam.training.homework.familybank.domain.Account;
import com.epam.training.homework.familybank.service.AccountOverviewService;
import com.epam.training.homework.familybank.service.PopulateDatabaseService;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

public class SpringConfigurationService {

    @Bean
    public Account bank() {
        Account bank = new Account();
        bank.setBalance(new BigDecimal(0));
        bank.setAssets(new BigDecimal(0));
        bank.setDebts(new BigDecimal(0));
        return bank;
    }

    @Bean
    public AccountOverviewService accountOverviewService(AccountDao accountDao, TransactionDao transactionDao,
                                                         UserDao userDao) {
        return new AccountOverviewService(accountDao, transactionDao, userDao);
    }

    @Bean(initMethod = "populateDatabase")
    public PopulateDatabaseService populateDatabaseService() {
        return new PopulateDatabaseService();
    }
}
