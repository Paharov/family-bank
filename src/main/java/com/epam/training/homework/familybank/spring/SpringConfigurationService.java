package com.epam.training.homework.familybank.spring;

import com.epam.training.homework.familybank.domain.Account;
import com.epam.training.homework.familybank.service.PopulateDatabaseService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class SpringConfigurationService {

    @Bean
    public Account bank() {
        Account bank = new Account();
        bank.setAssets(new BigDecimal(0));
        bank.setDebts(new BigDecimal(0));
        return bank;
    }

    @Bean(initMethod = "populateDatabase")
    public PopulateDatabaseService populateDatabaseService() {
        return new PopulateDatabaseService();
    }
}
