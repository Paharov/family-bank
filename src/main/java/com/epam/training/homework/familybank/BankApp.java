package com.epam.training.homework.familybank;

import com.epam.training.homework.familybank.spring.SpringConfigurationDao;
import com.epam.training.homework.familybank.spring.SpringConfigurationJpa;
import com.epam.training.homework.familybank.spring.SpringConfigurationService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class BankApp {

    public BankApp() {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(
            SpringConfigurationDao.class,
            SpringConfigurationJpa.class,
            SpringConfigurationService.class);
    }

    public static void main(String[] args) {
        BankApp demo = new BankApp();
    }
}
