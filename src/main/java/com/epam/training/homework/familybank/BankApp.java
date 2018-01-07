package com.epam.training.homework.familybank;

import com.epam.training.homework.familybank.service.AccountOverviewService;
import com.epam.training.homework.familybank.spring.SpringConfigurationDao;
import com.epam.training.homework.familybank.spring.SpringConfigurationJpa;
import com.epam.training.homework.familybank.spring.SpringConfigurationService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.math.BigDecimal;

public class BankApp {

    private static final BigDecimal LENDING_RATE = new BigDecimal(0.001);
    private static final BigDecimal BORROWING_RATE = new BigDecimal(0.002);

    private final AccountOverviewService accountOverviewService;

    public BankApp() {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(
            SpringConfigurationDao.class,
            SpringConfigurationJpa.class,
            SpringConfigurationService.class);
        accountOverviewService = context.getBean(AccountOverviewService.class);
    }

    public static void main(String[] args) {
        BankApp demo = new BankApp();
        demo.simulate();
    }

    private void simulate() {
        System.out.println(accountOverviewService.getBalanceByName("Ben"));
    }
}
