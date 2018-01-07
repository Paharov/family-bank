package com.epam.training.homework.familybank.spring;

import com.epam.training.homework.familybank.dao.AccountDao;
import com.epam.training.homework.familybank.dao.TransactionDao;
import com.epam.training.homework.familybank.dao.UserDao;
import com.epam.training.homework.familybank.domain.Account;
import com.epam.training.homework.familybank.domain.User;
import com.epam.training.homework.familybank.service.AccountOverviewService;
import com.epam.training.homework.familybank.service.PopulateDatabaseService;
import com.epam.training.homework.familybank.service.TransactionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(SpringConfigurationJpa.class)
public class SpringConfigurationService {

    @Bean
    public User bankUser() {
        User bankUser = new User();
        bankUser.setName("BANK");
        return bankUser;
    }

    @Bean
    public Account bank(User bankUser) {
        Account bank = new Account();
        bank.setUser(bankUser);
        return bank;
    }

    @Bean
    public AccountOverviewService accountOverviewService(UserDao userDao) {
        return new AccountOverviewService(userDao);
    }

    @Bean
    public TransactionService transactionService(AccountDao accountDao, TransactionDao transactionDao,
                                                 UserDao userDao) {
        return new TransactionService(accountDao, transactionDao, userDao);
    }

    @Bean(initMethod = "populateDatabase")
    public PopulateDatabaseService populateDatabaseService() {
        return new PopulateDatabaseService();
    }
}
