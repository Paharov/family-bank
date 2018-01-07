package com.epam.training.homework.familybank.spring;

import com.epam.training.homework.familybank.dao.AccountDao;
import com.epam.training.homework.familybank.dao.TransactionDao;
import com.epam.training.homework.familybank.dao.UserDao;
import com.epam.training.homework.familybank.dao.jpaimpl.JpaAccountDao;
import com.epam.training.homework.familybank.dao.jpaimpl.JpaTransactionDao;
import com.epam.training.homework.familybank.dao.jpaimpl.JpaUserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;

@Configuration
public class SpringConfigurationDao {

    @Bean
    public AccountDao accountDao() {
        return new JpaAccountDao();
    }

    @Bean
    public TransactionDao transactionDao() {
        return new JpaTransactionDao();
    }

    @Bean
    public UserDao userDao() {
        return new JpaUserDao();
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor petpp() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

}
